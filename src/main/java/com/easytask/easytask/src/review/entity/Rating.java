package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ratingId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @OneToMany(mappedBy = "rating")
    private List<RelatedAbilityRating> relatedAbilityRatingList = new ArrayList<>();

    @OneToMany(mappedBy = "rating")
    private List<PersonalAbilityRating> personalAbilityRatingList = new ArrayList<>();

    @Builder
    public Rating(Review review) {
        this.review = review;
    }

    public void addRelatedAbilityRating(RelatedAbilityRating relatedAbilityRating) {
        this.relatedAbilityRatingList.add(relatedAbilityRating);
    }

    public void addPersonalAbilityRating(PersonalAbilityRating personalAbilityRating) {
        this.personalAbilityRatingList.add(personalAbilityRating);
    }
}
