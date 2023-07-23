package com.easytask.easytask.common.scheduler;

import com.easytask.easytask.src.task.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingMailScheduler {
    private final Job job;
    private final JobLauncher jobLauncher;
    private final MatchingRequest matchingRequest;

    @Scheduled(fixedDelay = 15000)
    public void startJob() throws JobExecutionException {

        List<Task> taskList = matchingRequest.getTaskList();
        if (taskList.isEmpty()) {
            return;
        }
        LocalDateTime koreaStandardTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String kstString = koreaStandardTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", kstString)
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        String jobName = jobExecution.getJobInstance().getJobName();
        log.info("Matching Mail Batch Process, {} is Running...", jobName);

        matchingRequest.moveIndex();
    }
}
