package com.easytask.easytask.src.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AbilitySettingRequestDto {

    private List<Map<String,String>> possibleTask; //큰카테고리 작은카테고리

    private List<Map<String,String>> taskAbility;
}
