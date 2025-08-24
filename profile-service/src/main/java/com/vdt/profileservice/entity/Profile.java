package com.vdt.profileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class Profile {

  @Id
  @Column(name = "user_id")
  Long userId;
  LocalDateTime createdAt;
  LocalDateTime modifiedAt;
  String email;

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    modifiedAt = LocalDateTime.now();
  }

  String displayName;
  String bio;
  String avatarUrl;
}
