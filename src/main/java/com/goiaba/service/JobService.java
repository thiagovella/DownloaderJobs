package com.goiaba.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goiaba.model.BoardEntity;
import com.goiaba.model.JobEntity;
import com.goiaba.model.JobFilterEntity;
import com.goiaba.model.enumeration.JobMode;
import com.goiaba.model.enumeration.JobStatus;
import com.goiaba.repository.BoardRepository;
import com.goiaba.repository.JobFilterRepository;
import com.goiaba.repository.JobRepository;

@Service
public class JobService {
	
	@Autowired
	public JobRepository jobRepository;
	
	@Autowired
	public JobFilterRepository jobFilterRepository;
	
	@Autowired
	public BoardRepository boardRepository;
	
	public List<JobFilterEntity> findJobFilters(Long idJob) {
		JobEntity job = jobRepository.findOne(idJob);
		return jobFilterRepository.findByJob(job);
	}
	
	public void addJobFilter(Long jobId, Long filters[]) {
		JobEntity job = jobRepository.findOne(jobId);
		if (job == null) {
			return;
		}

		if (job.getFilters() == null) {
			job.setFilters(new ArrayList<JobFilterEntity>());
		}

		for (Long filter : filters) {
			if (jobFilterRepository.findByJobAndIdThread(job, filter) == null) {
				JobFilterEntity jobFilter = new JobFilterEntity();
				jobFilter.setIdThread(filter);
				jobFilter.setJob(job);
				job.getFilters().add(jobFilter);
			}
		}

		jobRepository.save(job);
	}
	
	public void addJob(String boardName) {
		BoardEntity board = boardRepository.findByName(boardName);
		if (board == null) {
			board = new BoardEntity();
			board.setName(boardName);
			boardRepository.save(board);
		}

		JobEntity job = new JobEntity();
		job.setBoard(board);
		job.setCreationDate(new Date());
		job.setStatus(JobStatus.ACTIVE);
		job.setMode(JobMode.FILTER);

		jobRepository.save(job);
	}
	
	public List<JobEntity> findAllJobs() {
		return jobRepository.findAll();
	}
}
