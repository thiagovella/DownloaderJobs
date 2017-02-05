package com.goiaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import com.goiaba.model.ThreadFileEntity;

public interface ThreadFileRepository extends JpaRepository<ThreadFileEntity, Long>, JpaSpecificationExecutor<ThreadFileEntity> {
  ThreadFileEntity findByIdThreadFile(@Param("idThreadFile") String idThreadFile);
}
