package com.goiaba.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.goiaba.model.enumeration.ThreadFileStatus;

@Entity
@Table(name="thread_file")
public class ThreadFileEntity {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "id_thread_file")
  private String idThreadFile;
  
  @ManyToOne
  @JoinColumn(name = "id_thread")
  private ThreadEntity thread;
  
  @Column(name = "creation_date")
  private Date creationDate;
  
  @Column(name = "file_name")
  private String name;
  
  @Column(name = "file_extension")
  private String extension;
  
  @Column(name = "file_size")
  private Double size;
  
  @Column(name = "file_size_str")
  private String sizeStr;
  
  @Column(name = "file_dimension_str")
  private String dimensionStr;
  
  @Column(name = "file_location_url")
  private String locationURL;
  
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ThreadFileStatus status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ThreadEntity getThread() {
    return thread;
  }

  public void setThread(ThreadEntity thread) {
    this.thread = thread;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getLocationURL() {
    return locationURL;
  }

  public void setLocationURL(String locationURL) {
    this.locationURL = locationURL;
  }

  public ThreadFileStatus getStatus() {
    return status;
  }

  public void setStatus(ThreadFileStatus status) {
    this.status = status;
  }

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
  }

  public String getSizeStr() {
    return sizeStr;
  }

  public void setSizeStr(String sizeStr) {
    this.sizeStr = sizeStr;
  }

  public String getDimensionStr() {
    return dimensionStr;
  }

  public void setDimensionStr(String dimensionStr) {
    this.dimensionStr = dimensionStr;
  }

  public String getIdThreadFile() {
    return idThreadFile;
  }

  public void setIdThreadFile(String idThreadFile) {
    this.idThreadFile = idThreadFile;
  }
}
