package com.goiaba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.goiaba.model.JobEntity;
import com.goiaba.model.JobFilterEntity;

public interface JobFilterRepository extends JpaRepository<JobFilterEntity, Long>, JpaSpecificationExecutor<JobFilterEntity> {
  JobFilterEntity findByJobAndIdThread(@Param("job") JobEntity job, @Param("idThread") Long idThread);
  List<JobFilterEntity> findByJob(@Param("job") JobEntity job);
}
