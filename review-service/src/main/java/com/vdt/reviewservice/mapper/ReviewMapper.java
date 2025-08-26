package com.vdt.reviewservice.mapper;

import com.vdt.reviewservice.dto.request.CreateReviewRequest;
import com.vdt.reviewservice.dto.request.UpdateReviewRequest;
import com.vdt.reviewservice.dto.response.ReviewResponse;
import com.vdt.reviewservice.dto.response.ReviewStatisticResponse;
import com.vdt.reviewservice.entity.Review;
import com.vdt.reviewservice.entity.ReviewStatistic;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
  @Mapping(target = "user", ignore = true)
  ReviewResponse toReviewResponse(Review review);


  @Mapping(target = "totalLikes", ignore = true)
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "approved", ignore = true)
  Review toReviewFromCreateRequest(CreateReviewRequest request);


  @Mapping(target = "totalLikes", ignore = true)
  @Mapping(target = "approved", ignore = true)
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateReview(UpdateReviewRequest request,@MappingTarget Review review);


  ReviewStatisticResponse toReviewStatisticResponse(ReviewStatistic entity);
}
