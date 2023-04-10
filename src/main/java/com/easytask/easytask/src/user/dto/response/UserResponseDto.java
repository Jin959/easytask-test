package com.easytask.easytask.src.user.dto.response;

import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String password;
    private String name;

    private List<Map<String,String>> possibleTask = new ArrayList<>(); //큰카테고리 작은카테고리

    private List<Map<String,String>> taskAbility = new ArrayList<>();  //ex) 언어역량, 영어

    private Role role;

    public UserResponseDto(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.name=user.getName();
        this.role=user.getRole();

        user.getPossibleTaskList().forEach(a -> possibleTask
                .add(Collections.singletonMap(a.getCategoryBig(), a.getCategorySmall())));

        user.getTaskAbilityList().forEach(a -> taskAbility
                .add(Collections.singletonMap(a.getCategoryBig(), a.getCategorySmall())));
    }

}
