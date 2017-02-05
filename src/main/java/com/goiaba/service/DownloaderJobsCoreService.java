package com.goiaba.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.goiaba.model.BoardEntity;
import com.goiaba.model.JobEntity;
import com.goiaba.model.JobFilterEntity;
import com.goiaba.model.ThreadEntity;
import com.goiaba.model.ThreadFileEntity;
import com.goiaba.model.enumeration.JobStatus;
import com.goiaba.model.enumeration.ThreadFileStatus;
import com.goiaba.repository.BoardRepository;
import com.goiaba.repository.JobFilterRepository;
import com.goiaba.repository.JobRepository;
import com.goiaba.repository.ThreadFileRepository;
import com.goiaba.repository.ThreadRepository;

@Service
public class DownloaderJobsCoreService {

	@Autowired
	private ThreadRepository threadRepository;

	@Autowired
	private ThreadFileRepository threadFileRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobFilterRepository jobFilterRepository;

	@Autowired
	private DownloadQueueService downloadQueueService;

	public static boolean isThreadAlive(ThreadEntity thread) throws IOException {

		URL u = new URL("http://boards.4chan.org/" + thread.getBoard().getName() + "/thread/" + thread.getIdThread());
		HttpURLConnection huc = (HttpURLConnection) u.openConnection();
		huc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		huc.setRequestMethod("GET"); // OR huc.setRequestMethod ("HEAD");
		huc.connect();
		int code = huc.getResponseCode();
		if (code == 200) {
			return true;
		}
		return false;
	}

	public static String getURLText(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		return response.toString();
	}

	public void runJobs() throws Exception {
		List<JobEntity> listActiveJobs = jobRepository.findByStatus(JobStatus.ACTIVE);

		for (JobEntity job : listActiveJobs) {
			updateCatalog(job.getId());
		}
	}

	private String getBoardHTML(BoardEntity board) throws Exception {
		return getURLText(String.format("http://boards.4chan.org/%s/catalog", board.getName()));
	}

	private String getThreadHTML(ThreadEntity thread) throws Exception {
		return getURLText("http://boards.4chan.org/" + thread.getBoard().getName() + "/thread/" + thread.getIdThread());
	}

	public List<Future<ThreadFileEntity>> updateThread(ThreadEntity thread) throws Exception {
		List<Future<ThreadFileEntity>> listThreadFileQueued = new ArrayList<Future<ThreadFileEntity>>();

		final Pattern patternDiv = Pattern.compile("<div class=\"file\" id=\"(.*?)\">(.+?)</div>", Pattern.DOTALL);
		final Pattern patternLink = Pattern.compile("href=\"(.+?)\" target=\"_blank\">(.+?)</a>", Pattern.DOTALL);
		final Pattern patternProperties = Pattern.compile("</a> \\((.+?)\\)", Pattern.DOTALL);

		final Matcher matcher = patternDiv.matcher(getThreadHTML(thread));

		while (matcher.find()) {
			String threadFileID = matcher.group(1);

			ThreadFileEntity threadFile = (ThreadFileEntity) threadFileRepository.findByIdThreadFile(threadFileID);

			try {
				if (threadFile == null) {
					threadFile = new ThreadFileEntity();
					System.out.print(thread.toString());
					System.out.println(matcher.group(2));

					final Matcher matcherLink = patternLink.matcher(matcher.group(2));
					final Matcher matcherProperties = patternProperties.matcher(matcher.group(2));
					matcherLink.find();
					matcherProperties.find();
					String fileLink = matcherLink.group(1);
					String fileName = matcherLink.group(2);
					String fileProperties = matcherProperties.group(1);
					String[] filePropsList = fileProperties.split("\\,");
					String fileSize = "";
					String fileDimension = "";
					if (filePropsList != null && filePropsList.length == 2) {
						fileSize = filePropsList[0];
						fileDimension = filePropsList[1];
					}
					String[] fileNameParts = fileName.split("\\.");
					String fileExtension = "";
					if (fileNameParts != null && fileNameParts.length > 1) {
						fileExtension = fileNameParts[fileNameParts.length - 1];
					}

					threadFile.setIdThreadFile(threadFileID);
					threadFile.setLocationURL(fileLink);
					threadFile.setName(fileName);
					threadFile.setExtension(fileExtension);
					threadFile.setSizeStr(fileSize.trim());
					threadFile.setDimensionStr(fileDimension.trim());
					threadFile.setThread(thread);

					if (thread.getScheduled()) {
						threadFile.setStatus(ThreadFileStatus.QUEUED);
						listThreadFileQueued.add(downloadQueueService.queueDownload(threadFile));
					} else {
						threadFile.setStatus(ThreadFileStatus.REGISTERED);
					}

					threadFileRepository.save(threadFile);
				} else {
					// Confere se houve mudança de status
					if (thread.getScheduled() && threadFile.getStatus() != ThreadFileStatus.DOWNLOADED) {
						threadFile.setStatus(ThreadFileStatus.QUEUED);
						// TODO: queue download
						threadFileRepository.save(threadFile);
						listThreadFileQueued.add(downloadQueueService.queueDownload(threadFile));
					}
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

		return listThreadFileQueued;
	}

	public void updateThreads(BoardEntity board, JobEntity job) {
		// Updates all threads that has been read before
		List<ThreadEntity> listThreads = threadRepository.findByBoardAndActive(board, true);
		List<JobFilterEntity> jobFiltersToRemove = new ArrayList<JobFilterEntity>();
		for (ThreadEntity thread : listThreads) {
			try {
				if (isThreadAlive(thread) == false) {
					thread.setActive(false);
					thread.setScheduled(false);
					thread.setUpdateDate(new Date());
					threadRepository.save(thread);

					JobFilterEntity jobFilter = jobFilterRepository.findByJobAndIdThread(job, thread.getIdThread());
					if (jobFilter != null) {
						jobFiltersToRemove.add(jobFilter);
						jobFilterRepository.delete(jobFilter);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateJobFilters(JobEntity job) {
		for (JobFilterEntity jobFilter : jobFilterRepository.findAll()) {
			ThreadEntity thread = threadRepository.findByIdThread(jobFilter.getIdThread());
			try {
				if (!isThreadAlive(thread)) {
					jobFilterRepository.delete(jobFilter);
					// TODO:
					//System.out.println("http://boards.4chan.org/" + thread.getBoard().getName() + "/thread/" + thread.getIdThread());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateCatalog(Long jobID) {
		JobEntity job = jobRepository.findOne(jobID);
		
		List<Future<ThreadFileEntity>> listThreadFileQueued = new ArrayList<Future<ThreadFileEntity>>();
		BoardEntity board = job.getBoard();
		
		// Regex get all threads by catalog
		final Pattern patternJson = Pattern.compile("var catalog =(.+?)};");
		Matcher matcher = null;
		try {
			matcher = patternJson.matcher(getBoardHTML(job.getBoard()));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String jsonString = "";
		while (matcher.find()) {
			jsonString = matcher.group(1) + "}};";
		}
		JSONObject jsonObj = new JSONObject(jsonString);
		JSONObject jsonThreads = ((JSONObject) jsonObj.get("threads"));

		Iterator<?> keys = jsonThreads.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();

			ThreadEntity thread = (ThreadEntity) threadRepository.findByIdThread(new Long(key));
			JSONObject jsonThread = (JSONObject) jsonThreads.get(key);

			boolean updateThread = false;

			// New Thread
			if (thread == null) {
				thread = new ThreadEntity();
				thread.setActive(true);
				thread.setIdThread(new Long(key));
				thread.setName(jsonThread.get("sub").toString());
				thread.setDescription(jsonThread.get("teaser").toString());
				thread.setCreationDate(new Date());
				thread.setUpdateDate(new Date());
				thread.setImages(Integer.parseInt(jsonThread.get("i").toString()));
				thread.setReplies(Integer.parseInt(jsonThread.get("r").toString()));
				thread.setScheduled(false);
				thread.setBoard(board);
			} else {
				// Update Thread
				thread.setActive(true);
				if (Integer.parseInt(jsonThread.get("i").toString()) > thread.getImages()) {
				//	updateThread = true;
				}
				thread.setImages(Integer.parseInt(jsonThread.get("i").toString()));
				thread.setReplies(Integer.parseInt(jsonThread.get("r").toString()));
				thread.setUpdateDate(new Date());
			}

			JobFilterEntity jobFilter = jobFilterRepository.findByJobAndIdThread(job, thread.getIdThread());
			if (jobFilter != null) {
				thread.setScheduled(true);
				updateThread = true;
			}

			threadRepository.save(thread);

			if (updateThread) {
				try {
					listThreadFileQueued.addAll(updateThread(thread));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		job.setLastRunDate(new Date());
		jobRepository.save(job);
		board.setLastUpdate(new Date());
		boardRepository.save(board);

		// update all files
		for (Future<ThreadFileEntity> threadFile : listThreadFileQueued) {
			while (!threadFile.isDone()) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				threadFileRepository.save(threadFile.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		updateThreads(board, job);
		updateJobFilters(job);
	}

}
