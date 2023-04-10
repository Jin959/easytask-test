package com.easytask.easytask.src.task.dto.response;

import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedAbilityResponseDto {
    private Long relatedAbilityId;
    private String relatedAbility;

    public RelatedAbilityResponseDto(RelatedAbility relatedAbility) {
        this.relatedAbilityId = relatedAbility.getId();
        this.relatedAbility = relatedAbility.getCategorySmall();
    }
}
