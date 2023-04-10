package com.easytask.easytask.src.review.repository;

import com.easytask.easytask.src.review.entity.PersonalAbilityRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAbilityRatingRepository extends JpaRepository<PersonalAbilityRating, Long> {
}
