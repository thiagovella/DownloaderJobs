package com.goiaba.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduleService {
  
  private static final Logger log = LoggerFactory.getLogger(JobScheduleService.class);
  
  @Value(value="${job_scheduler.enabled}")
  private boolean enabled;
  
  @Autowired 
  private DownloaderJobsCoreService downloaderJobsCoreService;
  
  @Scheduled(cron = "${job.cron}")
  @Async
  public void runJob() {
    if (enabled) {
      try {
        downloaderJobsCoreService.runJobs();
      } catch (Exception e) {
        log.error("Erro ao rodar Jobs", e);
      }
    }
  }
}
