package com.easytask.easytask.common.scheduler;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.util.MailGenerator;
import com.easytask.easytask.common.util.MailService;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskMail;
import com.easytask.easytask.src.task.repository.TaskMailRepository;
import com.easytask.easytask.src.user.entity.User;
import com.easytask.easytask.src.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.easytask.easytask.common.response.BaseResponseStatus.MAIL_SEND_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingMailScheduler {
    private final TaskMailRepository taskMailRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final MatchingRequest matchingRequest;

    @Scheduled(fixedDelay = 15000)
    public void matchingSchedule() {
        List<Task> taskList = matchingRequest.getTaskList();
        if (taskList.isEmpty()) {
            return;
        }
        try {
            Task task = matchingRequest.getScheduledTask();
            matchingMailing(task);
            matchingRequest.moveIndex();
        } catch (BaseException exception) {
            exception.printStackTrace();
        }
    }

    @Transactional
    private void matchingMailing(Task task) {
        log.info("Matching Mail Process is Running...");

        List<RelatedAbility> relatedAbilityList = task.getRelatedAbilityList();

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("taskId", task.getId());
        parameterValues.put("taskCategorySmall", task.getCategorySmall());
        parameterValues.put("relatedAbilityList", relatedAbilityList);
        parameterValues.put("abilityCount", relatedAbilityList.size());
        List<User> matchedIrumiList = userMapper.findUserOnMatchingMail(parameterValues);

        List<TaskMail> taskMailList = matchedIrumiList.stream().map(irumi -> {
            try {
                mailService.sendMatchingMail(new MailGenerator().createMatchingInvitation(task, irumi));
                return TaskMail.builder()
                        .irumi(irumi)
                        .task(task)
                        .build();
            } catch (Exception exception) {
                throw new BaseException(MAIL_SEND_ERROR);
            }
        }).collect(Collectors.toList());

        taskMailRepository.saveAll(taskMailList);
    }
}
