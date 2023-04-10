package com.easytask.easytask.src.task.dto.request;

import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatedAbilityRequestDto {
    private String categoryBig;
    private String categorySmall;
}
