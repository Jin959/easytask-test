package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalAbility extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personalAbilityId")
    private Long id;
    private String personalAbility;
    public PersonalAbility(String personalAbility) {
        this.personalAbility = personalAbility;
    }
}
 