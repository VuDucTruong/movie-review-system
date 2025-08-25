package com.vdt.reviewservice.controller;

import com.vdt.reviewservice.dto.ApiResponse;
import com.vdt.reviewservice.dto.PageResponse;
import com.vdt.reviewservice.dto.request.CreateReviewRequest;
import com.vdt.reviewservice.dto.request.UpdateReviewRequest;
import com.vdt.reviewservice.dto.response.ReviewResponse;
import com.vdt.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewController {

  ReviewService reviewService;


  @GetMapping("/{movieId}")
  ApiResponse<PageResponse<ReviewResponse>> getReviewPageByMovieId(@PathVariable Long movieId,
      Pageable pageable) {
    var reviewPage = reviewService.getReviewPageByMovieId(movieId, pageable);
    return ApiResponse.<PageResponse<ReviewResponse>>builder()
        .data(PageResponse.fromPage(reviewPage))
        .build();
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('USER_CREATE', 'ADMIN_CREATE')")
  ApiResponse<ReviewResponse> createReview(@RequestBody @Valid CreateReviewRequest createReviewRequest) {
    return ApiResponse.<ReviewResponse>builder()
        .data(reviewService.createReview(createReviewRequest))
        .build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER_UPDATE')")
  ApiResponse<ReviewResponse> updateReview(@PathVariable Long id,
      @RequestBody @Valid UpdateReviewRequest updateReviewRequest) {
    return ApiResponse.<ReviewResponse>builder()
        .data(reviewService.updateReview(id, updateReviewRequest))
        .build();
  }

  @PutMapping("/{id}/approve")
  @PreAuthorize("hasAnyRole('ADMIN_UPDATE')")
  ApiResponse<Boolean> approveReview(@PathVariable Long id) {

    boolean isApproved = reviewService.approveReview(id);

    return ApiResponse.<Boolean>builder()
        .message(isApproved ? "Approve successfully" : "Deny successfully").data(isApproved)
        .build();
  }

  @PostMapping("/{id}/like")
  ApiResponse<Boolean> likeReview(@PathVariable Long id) {
    boolean isLike = reviewService.likeReview(id);
    return ApiResponse.<Boolean>builder().message(isLike ? "Like successfully" : "Unlike successfully")
        .data(isLike).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER_DELETE', 'ADMIN_DELETE')")
  ApiResponse<Void> deleteReview(@PathVariable Long id) {
    reviewService.deleteReview(id);
    return ApiResponse.<Void>builder().message("Delete successfully").build();
  }


}
