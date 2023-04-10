package com.easytask.easytask.src.review.dto.response;

import com.easytask.easytask.src.review.entity.PersonalAbility;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PersonalAbilityResponseDto {
    private Long id;
    private String personalAbility;

    public PersonalAbilityResponseDto(PersonalAbility personalAbility) {
        this.id = personalAbility.getId();;
        this.personalAbility = personalAbility.getPersonalAbility();
    }
}
