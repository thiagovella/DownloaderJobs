package com.goiaba.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import com.goiaba.model.BoardEntity;
import com.goiaba.model.ThreadEntity;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Long>, JpaSpecificationExecutor<ThreadEntity> {
  
  ThreadEntity findByIdThread(@Param("idThread") Long idThread);
  
  List<ThreadEntity> findByBoardAndActive(@Param("board") BoardEntity board, @Param("active") boolean active);
}
