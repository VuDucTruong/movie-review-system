package com.vdt.reviewservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewStatistic {

  @Id
  Long movieId;
  long review5Count;
  long review4Count;
  long review3Count;
  long review2Count;
  long review1Count;

  @Transient
  public long getTotalReviews() {
    return review1Count + review2Count + review3Count + review4Count + review5Count;
  }

  @Transient
  public double getAverageRating() {
    long totalReviews = getTotalReviews();
    if (totalReviews == 0) {
      return 0.0;
    }
    long totalScore = review1Count + review2Count * 2 + review3Count * 3 + review4Count * 4 + review5Count * 5;
    return Math.round((double) totalScore / totalReviews * 10) / 10.0;
  }
}
