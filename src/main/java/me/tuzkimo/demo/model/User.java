package me.tuzkimo.demo.model;

import java.time.Instant;

public class User {
  private Long id;

  private String uid;

  private String name;

  private String password;

  private Boolean admin;

  private Boolean banned;

  private Instant gmtCreate;

  private Instant gmtModified;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public Boolean getBanned() {
    return banned;
  }

  public void setBanned(Boolean banned) {
    this.banned = banned;
  }

  public Instant getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Instant gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public Instant getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Instant gmtModified) {
    this.gmtModified = gmtModified;
  }
}