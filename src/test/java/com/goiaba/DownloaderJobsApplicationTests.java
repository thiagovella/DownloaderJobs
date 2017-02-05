package com.goiaba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.goiaba.model.JobEntity;
import com.goiaba.model.JobFilterEntity;
import com.goiaba.model.enumeration.JobMode;
import com.goiaba.model.enumeration.JobStatus;
import com.goiaba.repository.BoardRepository;
import com.goiaba.repository.JobFilterRepository;
import com.goiaba.repository.JobRepository;
import com.goiaba.repository.ThreadRepository;
import com.goiaba.service.DownloaderJobsCoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloaderJobsApplicationTests {

  @Autowired
  public DownloaderJobsCoreService downloadJobsCoreService;
  
  @Autowired 
  public JobRepository jobRepository;
  
  @Autowired 
  public JobFilterRepository jobFilterRepository;
  
  
  @Autowired 
  public ThreadRepository threadRepository;
  
  @Autowired 
  public BoardRepository boardRepository;
  
  public static String getText(String url) throws Exception {
      URL website = new URL(url);
      URLConnection connection = website.openConnection();
      connection.addRequestProperty("User-Agent", 
    		  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
      BufferedReader in = new BufferedReader(
                              new InputStreamReader(
                                  connection.getInputStream()));

      StringBuilder response = new StringBuilder();
      String inputLine;

      while ((inputLine = in.readLine()) != null) 
          response.append(inputLine);

      in.close();

      return response.toString();
  }
  
  	@Test
  	public void testUpdateCatalog() throws Exception {
  		
  		
  	//	downloadJobsCoreService.updateCatalog(jobRepository.findOne(1L));
  		
  	}
  
  	@Ignore
  	@Test
  	public void testGetURL() throws Exception {
  		String content = getText("http://boards.4chan.org/gif/catalog");
//  		String out = new Scanner(new URL("http://boards.4chan.org/gif/catalog").openStream(), "UTF-8").useDelimiter("\\A").next();
  		
  		Assert.assertNotNull(content);
  	}
  
	@Test
	@Ignore
	public void contextLoads() throws IOException {
	  
	  JobEntity job = new JobEntity();
	  job.setBoard(boardRepository.findOne(1L));
	  job.setMode(JobMode.FILTER);
	  job.setCreationDate(new Date());
	  job.setLastRunDate(null);
	  job.setStatus(JobStatus.ACTIVE);
	  
	  List<JobFilterEntity> filters = new ArrayList<JobFilterEntity>();
	  JobFilterEntity filter = new JobFilterEntity();
	  filter.setIdThread(58104809L);
	  filter.setJob(job);
	  filters.add(filter);
	  
	  job.setFilters(filters);
	  
	  JobFilterEntity jobFilterE = new JobFilterEntity();
	  jobFilterE.setIdThread(5810480L);
	  jobFilterE.setJob(jobRepository.findOne(1L));
	  jobFilterRepository.save(jobFilterE);
//	  jobFilterRepository.findByJobAndIdThread(jobRepository.findOne(1L), 5810480L);
	  
//	  jobRepository.save(job);
	  
	  //catalogParser.updateThread(threadRepository.findOne(273L));
	  //catalogParser.updateCatalog(jobRepository.findOne(1L));
	 
	}

}
