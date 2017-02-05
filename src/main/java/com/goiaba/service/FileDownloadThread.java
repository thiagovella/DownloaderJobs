package com.goiaba.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goiaba.model.ThreadFileEntity;
import com.goiaba.model.enumeration.ThreadFileStatus;

public class FileDownloadThread implements Callable<ThreadFileEntity> {

	private static final Logger log = LoggerFactory.getLogger("historyDownload");

	private ThreadFileEntity threadFile;

	@Override
	public ThreadFileEntity call() {
		URL source;
		try {
			source = new URL("http:" + threadFile.getLocationURL());
			File destination = new File(
					"c:\\temp\\" + threadFile.getThread().getBoard().getName() + "\\" + threadFile.getThread().generateFileDir() + "\\" + threadFile.getName());

			FileUtils.copyURLToFile(source, destination);
			threadFile.setStatus(ThreadFileStatus.DOWNLOADED);
			threadFile.setCreationDate(new Date());
			
			log.debug("Downloaded file: " + threadFile.getThread().generateFileDir() + "\\" + threadFile.getName());
		} catch (IOException e) {
			e.printStackTrace();
			threadFile.setStatus(ThreadFileStatus.NOT_FOUND);
			log.debug("File not found: " + threadFile.getThread().generateFileDir() + "\\" + threadFile.getName());
		}

		return threadFile;
	}

	public ThreadFileEntity getThreadFile() {
		return threadFile;
	}

	public void setThreadFile(ThreadFileEntity threadFile) {
		this.threadFile = threadFile;
	}

}
