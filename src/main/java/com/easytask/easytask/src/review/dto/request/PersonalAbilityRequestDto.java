package com.easytask.easytask.src.review.dto.request;

import com.easytask.easytask.src.review.entity.PersonalAbility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalAbilityRequestDto {

    private String personalAbility;

    public PersonalAbility toEntity(PersonalAbilityRequestDto personalAbilityRequestDto) {
        PersonalAbility personalAbility = new PersonalAbility(personalAbilityRequestDto.getPersonalAbility());
        return personalAbility;
    }
}
