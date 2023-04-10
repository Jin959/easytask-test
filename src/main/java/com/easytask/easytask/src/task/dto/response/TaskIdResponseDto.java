package com.easytask.easytask.src.task.dto.response;

import com.easytask.easytask.src.task.entity.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskIdResponseDto {
    private Long taskId;
    private String matchingStatus;

    public TaskIdResponseDto(Task task) {
        this.taskId = task.getId();
        this.matchingStatus = task.getMatchingStatus().name();
    }
}
