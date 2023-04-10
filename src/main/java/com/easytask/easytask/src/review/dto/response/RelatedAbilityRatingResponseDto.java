package com.easytask.easytask.src.review.dto.response;

import com.easytask.easytask.src.review.entity.RelatedAbilityRating;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RelatedAbilityRatingResponseDto {

    private String relatedAbility;
    private int relatedAbilityRating;

    public RelatedAbilityRatingResponseDto(RelatedAbilityRating relatedAbilityRating) {
        this.relatedAbility = relatedAbilityRating.getRelatedAbility().getCategorySmall();
        this.relatedAbilityRating = relatedAbilityRating.getRelatedAbilityRating();
    }
}
