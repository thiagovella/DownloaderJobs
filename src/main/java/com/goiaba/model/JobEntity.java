package com.goiaba.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.goiaba.model.enumeration.JobMode;
import com.goiaba.model.enumeration.JobStatus;

@Entity
@Table(name="job")
public class JobEntity {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_board")
  private BoardEntity board;
  
  @Column(name = "refresh_time")
  private Integer refreshTime;
  
  @Column(name = "job_status")
  @Enumerated(EnumType.STRING)
  private JobStatus status;
  
  @Column(name = "job_mode")
  @Enumerated(EnumType.STRING)
  private JobMode mode;
  
  @Column(name = "creation_date")
  private Date creationDate;
  
  @Column(name = "last_run_date")
  private Date lastRunDate;
 
  @OneToMany(mappedBy = "job", fetch=FetchType.EAGER)
  @Cascade({CascadeType.REMOVE, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
  private List<JobFilterEntity> filters;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BoardEntity getBoard() {
    return board;
  }

  public void setBoard(BoardEntity board) {
    this.board = board;
  }

  public Integer getRefreshTime() {
    return refreshTime;
  }

  public void setRefreshTime(Integer refreshTime) {
    this.refreshTime = refreshTime;
  }

  public JobStatus getStatus() {
    return status;
  }

  public void setStatus(JobStatus status) {
    this.status = status;
  }

  public JobMode getMode() {
    return mode;
  }

  public void setMode(JobMode mode) {
    this.mode = mode;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getLastRunDate() {
    return lastRunDate;
  }

  public void setLastRunDate(Date lastRunDate) {
    this.lastRunDate = lastRunDate;
  }

  public List<JobFilterEntity> getFilters() {
    return filters;
  }

  public void setFilters(List<JobFilterEntity> filters) {
    this.filters = filters;
  }
}
