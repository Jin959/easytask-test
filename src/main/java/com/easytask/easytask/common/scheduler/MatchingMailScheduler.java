package com.easytask.easytask.common.scheduler;

import com.easytask.easytask.src.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingMailScheduler {
    private final TaskService taskService;
    private final MatchingRequest matchingRequest;

    @Scheduled(fixedDelay = 15000)
    public void matchingSchedule() {
        List<Long> taskList = matchingRequest.getTaskList();
        if (taskList.isEmpty()) {
            return;
        }
        log.info("Matching Mail Process is Running...");

        Long taskId = matchingRequest.getScheduledTaskId();
        taskService.updateTaskToMatching(taskId);
    }
}
