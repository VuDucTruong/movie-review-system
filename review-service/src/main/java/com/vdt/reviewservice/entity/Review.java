package com.vdt.reviewservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(nullable = false)
  Long userId;

  @Column(nullable = false)
  Long movieId;

  Integer rating = 0;

  String reviewText;

  long totalLikes;

  boolean approved = false;

  LocalDateTime createdAt;
  LocalDateTime modifiedAt;

  @PrePersist
  public void postPersist() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  public void postUpdate() {
    modifiedAt = LocalDateTime.now();
  }


}
