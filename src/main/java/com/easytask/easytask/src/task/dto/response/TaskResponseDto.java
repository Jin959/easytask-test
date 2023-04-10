package com.easytask.easytask.src.task.dto.response;

import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponseDto {
    private Long taskId;
    private String taskName;
    private String details;
    private String categoryBig;
    private String categorySmall;
    private Integer numberOfIrumi;
    private String matchingStatus;
    private List<RelatedAbilityInfo> relatedAbilityInfoList;
    private List<IrumiInfo> irumiInfoList;

    public TaskResponseDto(Task task, List<RelatedAbility> relatedAbilityList, List<TaskUserMapping> mappingList) {
        this.taskId = task.getId();
        this.taskName = task.getTaskName();
        this.details = task.getDetails();
        this.categoryBig = task.getCategoryBig();
        this.categorySmall = task.getCategorySmall();
        this.numberOfIrumi = task.getNumberOfIrumi();
        this.matchingStatus = task.getMatchingStatus().name();

        this.relatedAbilityInfoList = relatedAbilityList.stream().map(
                        ele -> new RelatedAbilityInfo(ele.getId(), ele.getCategoryBig(), ele.getCategorySmall())
                ).collect(Collectors.toList());

        this.irumiInfoList = mappingList.stream().map(
                        ele -> new IrumiInfo(ele.getIrumi().getId(), ele.getProgressStatus().name())
                ).collect(Collectors.toList());
    }

    public TaskResponseDto(Task task) {
        this.taskId = task.getId();
        this.taskName = task.getTaskName();
        this.details = task.getDetails();
        this.categoryBig = task.getCategoryBig();
        this.categorySmall = task.getCategorySmall();
        this.numberOfIrumi = task.getNumberOfIrumi();
        this.matchingStatus = task.getMatchingStatus().name();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class IrumiInfo {
        private Long irumiId;
        private String progressStatus;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class RelatedAbilityInfo {
        private Long relatedAbilityId;
        private String categoryBig;
        private String categorySmall;
    }
}
