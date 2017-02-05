package com.goiaba.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.goiaba.model.ThreadFileEntity;

@Service
public class DownloadQueueService {

	private final ExecutorService downloaderThreadPool;
	
	@Autowired
	DownloadQueueService(@Value("${downloader.threadpool_size}") int threadPoolSize) {
		downloaderThreadPool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	public Future<ThreadFileEntity> queueDownload(ThreadFileEntity threadFile) throws InterruptedException, ExecutionException {
		FileDownloadThread queueFile = new FileDownloadThread();
		queueFile.setThreadFile(threadFile);
		
		return downloaderThreadPool.submit(queueFile);
	}
	
}
