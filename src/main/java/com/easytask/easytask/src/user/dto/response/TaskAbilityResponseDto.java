package com.easytask.easytask.src.user.dto.response;

import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Getter
@NoArgsConstructor
public class TaskAbilityResponseDto {
    private List<Map<String,String>> taskList = new ArrayList<>(); //큰카테고리 작은카테고리

    public TaskAbilityResponseDto(User user){
        user.getTaskAbilityList().forEach(a -> taskList
                .add(Collections.singletonMap(a.getCategoryBig(), a.getCategorySmall())));
    }
}
