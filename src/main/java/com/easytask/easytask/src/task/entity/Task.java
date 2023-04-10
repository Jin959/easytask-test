package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.dto.request.TaskRequestDto;
import com.easytask.easytask.src.user.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId",nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User customer;

    private String taskName;

    private String details;

    private String categoryBig;

    private String categorySmall;


    private Integer numberOfIrumi;


    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskUserMapping> irumiList = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RelatedAbility> relatedAbilityList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus = MatchingStatus.STANDBY;

    public void updateTask(TaskRequestDto taskRequestDto) {
        this.taskName = taskRequestDto.getTaskName();
        this.details = taskRequestDto.getDetails();
        this.categoryBig = taskRequestDto.getCategoryBig();
        this.categorySmall = taskRequestDto.getCategorySmall();
        this.numberOfIrumi = taskRequestDto.getNumberOfIrumi();
    }

    public void deleteTask() {
        this.state = State.INACTIVE;
    }

    public void updateMatchingStatus(MatchingStatus matchingStatus) {
        this.matchingStatus = matchingStatus;
    }

    public enum MatchingStatus {
        STANDBY, MATCHING, MATCHED, DOING, DONE
    }

    @Builder
    public Task(User customer, String taskName, String details,
                String categoryBig, String categorySmall, Integer numberOfIrumi) {
        this.customer = customer;
        this.taskName = taskName;
        this.details = details;
        this.categoryBig = categoryBig;
        this.categorySmall = categorySmall;
        this.numberOfIrumi = numberOfIrumi;
    }
}
