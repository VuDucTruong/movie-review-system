package com.vdt.movieservice.repository.client;

import com.vdt.movieservice.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "reivew-service", url = "${app.services.review-service.url}")
public interface ReviewClient {
  @PostMapping("/statistic/{movieId}")
  ApiResponse<Void> createReviewStatistic(@PathVariable Long movieId);
}
