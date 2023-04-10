package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelatedAbility extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relatedAbilityId",nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    @JsonIgnore
    private Task task;

    private String categoryBig;

    private String categorySmall;

    @Builder
    public RelatedAbility(Task task, String categoryBig, String categorySmall) {
        this.task = task;
        this.categoryBig = categoryBig;
        this.categorySmall = categorySmall;
    }

    public void deleteRelatedAbility() {
        this.state = State.INACTIVE;
    }

}
