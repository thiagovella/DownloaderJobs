package com.goiaba.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import com.goiaba.model.JobEntity;
import com.goiaba.model.enumeration.JobStatus;

public interface JobRepository extends JpaRepository<JobEntity, Long>, JpaSpecificationExecutor<JobEntity> {

  List<JobEntity> findByStatus(@Param("status") JobStatus status);
}
