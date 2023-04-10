package com.easytask.easytask.common.scheduler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Component
public class MatchingRequest {

    private List<Long> taskList = new ArrayList<>();
    private Integer scheduledIndex = 0;

    public void addTask(Long taskId) {
        taskList.add(taskId);
        log.info("after add matchingRequest size : {}", taskList.size());
    }

    public Long getScheduledTaskId() {
        return taskList.get(scheduledIndex);
    }

    public void moveIndex() {
        this.scheduledIndex = (scheduledIndex + 1) % taskList.size();
    }

    public void removeTask(Long taskId) {
        taskList.remove(taskId);
        log.info("after matchingRequest size : {}", taskList.size());
    }
}
