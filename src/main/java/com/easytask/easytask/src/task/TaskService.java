package com.easytask.easytask.src.task;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.scheduler.MatchingRequest;
import com.easytask.easytask.common.util.MailGenerator;
import com.easytask.easytask.common.util.MailService;
import com.easytask.easytask.src.task.dto.request.RelatedAbilityRequestDto;
import com.easytask.easytask.src.task.dto.response.RelatedAbilityResponseDto;
import com.easytask.easytask.src.task.dto.response.TaskResponseDto;
import com.easytask.easytask.src.task.dto.request.TaskRequestDto;
import com.easytask.easytask.src.task.dto.response.TaskIdResponseDto;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskMail;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.task.repository.RelatedAbilityRepository;
import com.easytask.easytask.src.task.repository.TaskMailRepository;
import com.easytask.easytask.src.task.repository.TaskRepository;
import com.easytask.easytask.src.task.repository.TaskUserMappingRepository;
import com.easytask.easytask.src.user.entity.User;
import com.easytask.easytask.src.user.repository.UserMapper;
import com.easytask.easytask.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.easytask.easytask.common.BaseEntity.State.ACTIVE;
import static com.easytask.easytask.common.response.BaseResponseStatus.*;
import static com.easytask.easytask.src.task.entity.TaskUserMapping.ProgressStatus.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final RelatedAbilityRepository relatedAbilityRepository;
    private final TaskMailRepository taskMailRepository;
    private final TaskUserMappingRepository mappingRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final MatchingRequest matchingRequest;

    public TaskIdResponseDto createTask(Long customerId, TaskRequestDto taskRequestDto) {
        User customer = userRepository.findByIdAndState(customerId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        try {
            Task task = Task.builder()
                    .customer(customer)
                    .taskName(taskRequestDto.getTaskName())
                    .details(taskRequestDto.getDetails())
                    .categoryBig(taskRequestDto.getCategoryBig())
                    .categorySmall(taskRequestDto.getCategorySmall())
                    .numberOfIrumi(taskRequestDto.getNumberOfIrumi())
                    .build();

            taskRepository.save(task);

            return new TaskIdResponseDto(task);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTask(Long taskId) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        try {
            List<RelatedAbility> relatedAbilityList = relatedAbilityRepository.findAllByTaskIdAndState(taskId, ACTIVE);
            List<TaskUserMapping> mappingList = mappingRepository.findAllByTaskIdAndState(taskId, ACTIVE);

            return new TaskResponseDto(task, relatedAbilityList, mappingList);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public TaskIdResponseDto updateTask(Long taskId, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));

        try {
            task.updateTask(taskRequestDto);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }

        return new TaskIdResponseDto(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));

        try {
            task.deleteTask();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public RelatedAbilityResponseDto postRelatedAbility(
            Long taskId, RelatedAbilityRequestDto relatedAbilityRequestDto) {

        relatedAbilityRepository.findByCategorySmallAndState(
                relatedAbilityRequestDto.getCategorySmall(), ACTIVE
        ).ifPresent((ability) -> {
            throw new BaseException(BAD_REQUEST_DUPLICATE_RELATED_ABILITY);
        });

        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));

        try {
            RelatedAbility relatedAbility = RelatedAbility.builder()
                    .task(task)
                    .categoryBig(relatedAbilityRequestDto.getCategoryBig())
                    .categorySmall(relatedAbilityRequestDto.getCategorySmall())
                    .build();
            relatedAbilityRepository.save(relatedAbility);
            return new RelatedAbilityResponseDto(relatedAbility);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public void deleteRelatedAbility(Long relatedAbilityId) {
        RelatedAbility relatedAbility = relatedAbilityRepository.findByIdAndState(relatedAbilityId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_ABILITY));
        try {
            relatedAbility.deleteRelatedAbility();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public void updateTaskToMatching(Long taskId) {
        Task task = taskRepository.findWithUserByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        Optional.ofNullable(task.getTaskName())
                .orElseThrow(() -> new BaseException(BAD_REQUEST_NO_TASK_NAME));
        Optional.ofNullable(task.getCategorySmall())
                .orElseThrow(() -> new BaseException(BAD_REQUEST_NO_CATEGORY));
        Optional.ofNullable(task.getDetails())
                .orElseThrow(() -> new BaseException(BAD_REQUEST_NO_DETAILS));

        if (task.getMatchingStatus() != Task.MatchingStatus.STANDBY) {
            throw new BaseException(BAD_REQUEST_ALREADY_QUEUED_MATCHING);
        }

        List<RelatedAbility> relatedAbilityList = task.getRelatedAbilityList();
        if (relatedAbilityList.isEmpty()) {
            throw new BaseException(BAD_REQUEST_NO_RELATED_ABILITY);
        }

        try {
            User customer = task.getCustomer();
            task.updateMatchingStatus(Task.MatchingStatus.MATCHING);
            matchingRequest.addTask(task);
            mailService.sendMatchingMail(new MailGenerator().createMatchingMail(task, customer));
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public void updateTaskToMatched(Long taskId, Long irumiId) {
        TaskMail taskMail = taskMailRepository
                .findWithTaskAndIrumiByTaskIdAndIrumiIdAndState(taskId, irumiId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TAKSMAIL));
        try {
            Task task = taskMail.getTask();
            User irumi = taskMail.getIrumi();
            User customer = task.getCustomer();

            Integer needCount = task.getNumberOfIrumi();
            Integer matchedCount = task.getIrumiList().size();
            if (checkMatchingIsOver(matchedCount, needCount)) {
                throw new BaseException(BAD_REQUEST_ALREADY_MATCHED);
            }
            taskMail.updateMailingStatusToAgree();

            TaskUserMapping taskUserMapping = TaskUserMapping.builder()
                    .task(task)
                    .irumi(irumi)
                    .build();
            mappingRepository.save(taskUserMapping);

            if (checkMatchingIsOver(matchedCount + 1, needCount)) {
                matchingRequest.removeTask(taskId);
                task.updateMatchingStatus(Task.MatchingStatus.MATCHED);
                mailService.sendMatchingMail(new MailGenerator().createMatchedMail(task, customer));
            }
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    private Boolean checkMatchingIsOver(Integer matchedCount, Integer needCount) {
        if (matchedCount == needCount) {
            return true;
        }
        return false;
    }

    public void updateTaskToDoing(Long taskId, Long irumiId) {
        Task task = taskRepository.findWithMappingByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        try {
            List<TaskUserMapping> irumiList = task.getIrumiList();
            updateProgressStatus(irumiId, irumiList, DOING);
            if (checkAllIrumiesProgressStatus(irumiList, DOING)) {
                task.updateMatchingStatus(Task.MatchingStatus.DOING);
            }
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public void updateTaskToDone(Long taskId, Long irumiId) {
        Task task = taskRepository.findWithMappingAndCustomerByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        User customer = task.getCustomer();
        try {
            List<TaskUserMapping> irumiList = task.getIrumiList();
            updateProgressStatus(irumiId, irumiList, DONE);
            if (checkAllIrumiesProgressStatus(irumiList, DONE)) {
                task.updateMatchingStatus(Task.MatchingStatus.DONE);
                mailService.sendMatchingMail(new MailGenerator().createTaskDoneMail(task, customer));
            }
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    private Boolean checkAllIrumiesProgressStatus(
            List<TaskUserMapping> irumiList, TaskUserMapping.ProgressStatus progressStatus) {
        int doingCount = 0;
        for (TaskUserMapping taskUserMapping : irumiList) {
            if (taskUserMapping.getProgressStatus() != progressStatus) {
                return false;
            }
        }
        return true;
    }

    private void updateProgressStatus(
            Long irumiId, List<TaskUserMapping> irumiList, TaskUserMapping.ProgressStatus progressStatus) {
        for (TaskUserMapping taskUserMapping : irumiList) {
            User irumi = taskUserMapping.getIrumi();
            if (irumi.getId() == irumiId) {
                taskUserMapping.updateProgressStatus(progressStatus);
                return;
            }
        }
        throw new BaseException(NOT_FOUND_MATCHING);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getCustomerMyTaskList(Long customerId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("updatedAt")));
        Page<Task> taskPage = taskRepository.findByCustomerIdAndState(customerId, ACTIVE, pageRequest)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        try {
            return  taskPage.map(TaskResponseDto::new).toList();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getIrumiMyTaskList(Long irumiId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("updatedAt")));
        Page<TaskUserMapping> taskUserMappingPage = mappingRepository.findWithTaskByIrumiIdAndState(irumiId, ACTIVE, pageRequest)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        try {
            return taskUserMappingPage.map(taskUserMapping -> {
                Task task = taskUserMapping.getTask();
                return new TaskResponseDto(task);
            }).toList();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }
}
