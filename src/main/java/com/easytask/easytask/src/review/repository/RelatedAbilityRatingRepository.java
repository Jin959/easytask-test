package com.easytask.easytask.src.review.repository;

import com.easytask.easytask.src.review.entity.RelatedAbilityRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatedAbilityRatingRepository extends JpaRepository<RelatedAbilityRating, Long>{

}
