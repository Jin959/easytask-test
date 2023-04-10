package com.easytask.easytask.src.user.entity;

import com.easytask.easytask.common.BaseEntity;

import com.easytask.easytask.src.user.dto.request.UserRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @Column(name = "userId", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<PossibleTask> possibleTaskList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<TaskAbility> taskAbilityList = new ArrayList<>();

    @Builder
    public User (String email, String password, String name,Role role){
        this.email=email;
        this.password=password;
        this.name=name;
        this.role=role;
    }
    public void updateUser(UserRequestDto userRequestDto){
        this.email= userRequestDto.getEmail();
        this.password= userRequestDto.getPassword();

    }

    public void addPossibleTask(PossibleTask possibleTask){
        this.possibleTaskList.add(possibleTask);
    }
    public void addTaskAbility(TaskAbility taskAbility){
        this.taskAbilityList.add(taskAbility);
    }
}
