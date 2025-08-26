package com.vdt.reviewservice.service.impl;

import com.vdt.event.NotificationEvent;
import com.vdt.reviewservice.dto.request.CreateReviewRequest;
import com.vdt.reviewservice.dto.request.UpdateReviewRequest;
import com.vdt.reviewservice.dto.response.ReviewResponse;
import com.vdt.reviewservice.dto.response.ReviewStatisticResponse;
import com.vdt.reviewservice.entity.ReviewLike;
import com.vdt.reviewservice.entity.ReviewLikeId;
import com.vdt.reviewservice.entity.ReviewStatistic;
import com.vdt.reviewservice.exception.AppException;
import com.vdt.reviewservice.exception.ErrorCode;
import com.vdt.reviewservice.mapper.ReviewMapper;
import com.vdt.reviewservice.repository.ReviewLikeRepository;
import com.vdt.reviewservice.repository.ReviewRepository;
import com.vdt.reviewservice.repository.ReviewStatisticRepository;
import com.vdt.reviewservice.repository.client.ProfileClient;
import com.vdt.reviewservice.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  ReviewRepository reviewRepository;
  ReviewLikeRepository reviewLikeRepository;
  ReviewMapper reviewMapper;
  ProfileClient profileClient;
  KafkaTemplate<String, Object> kafkaTemplate;
  ReviewStatisticRepository statisticRepository;
  private final ReviewStatisticRepository reviewStatisticRepository;


  @Override
  public ReviewResponse createReview(CreateReviewRequest createReviewRequest) {
    var review = reviewMapper.toReviewFromCreateRequest(createReviewRequest);

    review.setUserId(getUserIdFromJwt());

    var savedReview = reviewRepository.save(review);
    return reviewMapper.toReviewResponse(savedReview);
  }

  @Override
  public ReviewResponse updateReview(Long id, UpdateReviewRequest updateReviewRequest) {

    var review = reviewRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

    if (!getUserIdFromJwt().equals(review.getUserId())) {
      throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    reviewMapper.updateReview(updateReviewRequest, review);

    return reviewMapper.toReviewResponse(reviewRepository.save(review));
  }

  @Override
  public Page<ReviewResponse> getReviewPageByMovieId(Long movieId, Pageable pageable) {
    return reviewRepository.findByMovieId(movieId, pageable)
        .map(reviewMapper::toReviewResponse);
  }

  @Override
  public boolean approveReview(Long id) {
    var review = reviewRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
    review.setApproved(!review.isApproved());
    var userProfile = profileClient.getProfileByUserId(review.getUserId());
    var savedReview = reviewRepository.save(review);

    var notificationEvent = NotificationEvent.builder()
        .channel("EMAIL")
        .recipient(userProfile.getData().email())
        .subject("Your review has been " + (savedReview.isApproved() ? "approved" : "denied"))
        .body("Hello " + userProfile.getData().displayName() + ",<br>" +
            "Your review for the movie with ID " + review.getMovieId() + " has been " +
            (savedReview.isApproved() ? "approved" : "denied") + ".<br>" +
            "Thank you for your contribution!<br>" +
            "Best regards,<br>" +
            "Movie Review Team")
        .build();

    kafkaTemplate.send("review-approved", notificationEvent);

    return savedReview.isApproved();
  }

  @Override
  public void deleteReview(Long id) {
    reviewRepository.deleteById(id);
  }

  @Override
  public boolean likeReview(Long id) {

    var reviewLikeId = new ReviewLikeId(id, getUserIdFromJwt());

    var reviewLike = reviewLikeRepository.findById(reviewLikeId).orElse(null);

    if (reviewLike != null) {
      reviewLikeRepository.delete(reviewLike);
      return false;
    }

    reviewLikeRepository.save(new ReviewLike(reviewLikeId));

    return true;
  }

  @Override
  public ReviewStatisticResponse getReviewStatistic(Long movieId) {
    return reviewMapper.toReviewStatisticResponse(
        reviewStatisticRepository.findById(movieId)
            .orElse(ReviewStatistic.builder().movieId(movieId).build()));
  }

  @Override
  public void createEmptyStatistic(Long movieId) {
    reviewStatisticRepository.save(ReviewStatistic.builder().movieId(movieId).build());
  }

  private Long getUserIdFromJwt() {
    Jwt jwt = (Jwt) org.springframework.security.core.context.SecurityContextHolder.getContext()
        .getAuthentication().getCredentials();
    return Long.parseLong(jwt.getSubject());
  }
}
