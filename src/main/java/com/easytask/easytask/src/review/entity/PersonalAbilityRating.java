package com.easytask.easytask.src.review.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class PersonalAbilityRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PersonalAbilityRatingId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratingId")
    private Rating rating;

    @OneToOne
    @JoinColumn(name = "personalAbilityId")
    private PersonalAbility personalAbility;
    private int personalAbilityRating;

    @Builder
    public PersonalAbilityRating(Rating rating, PersonalAbility personalAbility, int personalAbilityRating) {
        this.rating = rating;
        this.personalAbility = personalAbility;
        this.personalAbilityRating = personalAbilityRating;
    }
}
