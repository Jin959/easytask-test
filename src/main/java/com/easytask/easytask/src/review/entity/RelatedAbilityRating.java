package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class RelatedAbilityRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relatedAbilityRatingId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratingId")
    private Rating rating;

    @OneToOne
    @JoinColumn(name = "relatedAbilityId")
    private RelatedAbility relatedAbility;
    private int relatedAbilityRating;

    @Builder
    public RelatedAbilityRating(Rating rating, RelatedAbility relatedAbility, int relatedAbilityRating) {
        this.rating = rating;
        this.relatedAbility = relatedAbility;
        this.relatedAbilityRating = relatedAbilityRating;
    }
}
