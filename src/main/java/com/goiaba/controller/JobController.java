package com.goiaba.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goiaba.model.JobEntity;
import com.goiaba.model.JobFilterEntity;
import com.goiaba.service.DownloaderJobsCoreService;
import com.goiaba.service.JobService;

@RestController
@RequestMapping("/downloaderJobs")
@CrossOrigin(origins="*")
public class JobController {

	@Autowired
	private DownloaderJobsCoreService downloaderJobsCoreService;

	@Autowired
	private JobService jobService;
	
	@RequestMapping(value = "/getJobFilters", method = RequestMethod.GET)
	public Collection<JobFilterEntity> getJobFilters(Long idJob) {
		return jobService.findJobFilters(idJob);
	}

	@RequestMapping(value = "/addJob", method = RequestMethod.POST)
	public void addJob(@RequestParam String boardName) {
		jobService.addJob(boardName);
	}

	@RequestMapping(value = "/addJobFilters", method = RequestMethod.POST)
	public void addJobFilter(Long jobId, Long[] filters) {
		jobService.addJobFilter(jobId, filters);
	}

	@RequestMapping(value = "/getAllJobs", method = RequestMethod.GET)
	public Collection<JobEntity> getAllJobs() {
		return jobService.findAllJobs();
	}

	@RequestMapping(value = "/startJob", method = RequestMethod.POST)
	public void startJob(@RequestParam Long jobID) throws Exception {
		downloaderJobsCoreService.updateCatalog(jobID);
	}

	@RequestMapping(value = "/startAllJobs", method = RequestMethod.POST)
	public void startJobs() throws Exception {
		downloaderJobsCoreService.runJobs();
	}
}
