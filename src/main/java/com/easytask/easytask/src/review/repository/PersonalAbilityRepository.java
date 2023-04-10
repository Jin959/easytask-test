package com.easytask.easytask.src.review.repository;


import com.easytask.easytask.src.review.entity.PersonalAbility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalAbilityRepository extends JpaRepository<PersonalAbility, Long> {

   Optional<PersonalAbility> findById(Long id);
}
