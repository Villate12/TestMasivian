package com.testmasivian.test;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Roulettes {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String status;

  private String createdAt;

  private String updatedAt;

  private String deletedAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
      this.status = status;
  }
  public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
  }
  public String getCreatedAt() {
      return createdAt;
  }
  public void setDeletedAt(String deletedAt) {
      this.deletedAt = deletedAt;
  }
  public String getDeletedAt() {
      return deletedAt;
  }
  public void setUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
  }
  public String getUpdatedAt() {
      return updatedAt;
  }
 }