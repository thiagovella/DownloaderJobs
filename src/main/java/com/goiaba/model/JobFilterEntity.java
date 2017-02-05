package com.goiaba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="job_filter")
public class JobFilterEntity {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "id_job")
  private JobEntity job;
  
  @Column(name = "id_thread")
  private Long idThread;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public JobEntity getJob() {
    return job;
  }

  public void setJob(JobEntity job) {
    this.job = job;
  }

  public Long getIdThread() {
    return idThread;
  }

  public void setIdThread(Long idThread) {
    this.idThread = idThread;
  }
}
