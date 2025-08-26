package com.vdt.reviewservice.service;

import com.vdt.reviewservice.dto.request.CreateReviewRequest;
import com.vdt.reviewservice.dto.request.UpdateReviewRequest;
import com.vdt.reviewservice.dto.response.ReviewResponse;
import com.vdt.reviewservice.dto.response.ReviewStatisticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
  ReviewResponse createReview(CreateReviewRequest createReviewRequest);

  ReviewResponse updateReview(Long id, UpdateReviewRequest updateReviewRequest);

  Page<ReviewResponse> getReviewPageByMovieId(Long movieId, Pageable pageable);

  boolean approveReview(Long id);

  void deleteReview(Long id);

  boolean likeReview(Long id); // include unlike also

  ReviewStatisticResponse getReviewStatistic(Long movieId);

  void createEmptyStatistic(Long movieId);
}
