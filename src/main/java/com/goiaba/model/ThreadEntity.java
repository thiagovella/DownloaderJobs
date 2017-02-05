package com.goiaba.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="thread")
public class ThreadEntity {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "id_thread")
  private Long idThread;
  
  @ManyToOne
  @JoinColumn(name = "id_board")
  private BoardEntity board;
  
  @Column(name = "name")
  private String name;
  
  @Column(name = "description")
  private String description;
  
  @Column(name = "images")
  private Integer images;
  
  @Column(name = "replies")
  private Integer replies;
  
  @Column(name = "active")
  private Boolean active;
  
  @Column(name = "scheduled")
  private Boolean scheduled;
  
  @Column(name = "creation_date")
  private Date creationDate;
  
  @Column(name = "update_date")
  private Date updateDate;
  
  @Column(name = "first_post_date")
  private Date firstPostDate;
  
  @Column(name = "last_post_date")
  private Date lastPostDate;
  
  public String generateFileDir() {
	  
	  String dir = "";
	  try {
	  if (name == null || name.isEmpty()) {
		  if (description.length() > 50)
			  dir = description.substring(0, 50);
		  else
			  dir = description;
			  
	  } else {
		  dir = name;
	  }
	  } catch (Exception exp) {
		exp.printStackTrace();  
	  }
	  
	  return dir.replaceAll("[^a-zA-Z0-9.-]", "_");
  }

  @Override
  public String toString() {
	  String des = "";
	  if (board != null) {
		  des += board.getName() + " - ";
	  }
	  des += name;
	return des;
  }
  
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public Date getFirstPostDate() {
    return firstPostDate;
  }

  public void setFirstPostDate(Date firstPostDate) {
    this.firstPostDate = firstPostDate;
  }

  public Date getLastPostDate() {
    return lastPostDate;
  }

  public void setLastPostDate(Date lastPostDate) {
    this.lastPostDate = lastPostDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getImages() {
    return images;
  }

  public void setImages(Integer images) {
    this.images = images;
  }

  public Integer getReplies() {
    return replies;
  }

  public void setReplies(Integer replies) {
    this.replies = replies;
  }

  public Long getIdThread() {
    return idThread;
  }

  public void setIdThread(Long idThread) {
    this.idThread = idThread;
  }

  public Boolean getScheduled() {
    return scheduled;
  }

  public void setScheduled(Boolean scheduled) {
    this.scheduled = scheduled;
  }
}
