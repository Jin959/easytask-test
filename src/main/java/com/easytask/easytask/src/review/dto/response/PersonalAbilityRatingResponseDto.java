package com.easytask.easytask.src.review.dto.response;

import com.easytask.easytask.src.review.entity.PersonalAbilityRating;
import com.easytask.easytask.src.review.entity.RelatedAbilityRating;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PersonalAbilityRatingResponseDto {

    private String personalAbility;
    private int personalAbilityRating;

    public PersonalAbilityRatingResponseDto(PersonalAbilityRating personalAbilityRating) {
        this.personalAbility = personalAbilityRating.getPersonalAbility().getPersonalAbility();
        this.personalAbilityRating = personalAbilityRating.getPersonalAbilityRating();
    }
}
