package com.vdt.reviewservice.repository;

import com.vdt.reviewservice.entity.ReviewLike;
import com.vdt.reviewservice.entity.ReviewLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {

}
