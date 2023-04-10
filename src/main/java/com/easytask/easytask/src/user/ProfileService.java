package com.easytask.easytask.src.user;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.src.user.dto.request.TaskRequestDto;
import com.easytask.easytask.src.user.dto.response.PossibleTaskResponseDto;
import com.easytask.easytask.src.user.dto.response.TaskAbilityResponseDto;
import com.easytask.easytask.src.user.entity.PossibleTask;
import com.easytask.easytask.src.user.entity.TaskAbility;
import com.easytask.easytask.src.user.entity.User;
import com.easytask.easytask.src.user.repository.PossibleTaskRepository;
import com.easytask.easytask.src.user.repository.TaskAbilityRepository;
import com.easytask.easytask.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.easytask.easytask.common.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final PossibleTaskRepository possibleTaskRepository;
    private final TaskAbilityRepository taskAbilityRepository;

    public PossibleTaskResponseDto getPossibleTask(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_FIND_USER));
        return new PossibleTaskResponseDto(user);
    }

    public TaskAbilityResponseDto getTaskAbility(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_FIND_USER));
        return new TaskAbilityResponseDto(user);
    }

    public PossibleTaskResponseDto addPossibleTask(TaskRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        System.out.println(requestDto.getUpdateTask());

        PossibleTask possibleTask = PossibleTask.builder()
                .user(user)
                .categoryBig(requestDto.getUpdateTask().keySet().iterator().next())
                .categorySmall(requestDto.getUpdateTask().get(requestDto.getUpdateTask().keySet().iterator().next()))
                .build();

        possibleTaskRepository.save(possibleTask);
        return new PossibleTaskResponseDto(user);
    }

    public TaskAbilityResponseDto addTaskAbility(TaskRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        TaskAbility taskAbility = TaskAbility.builder()
                .user(user)
                .categoryBig(requestDto.getUpdateTask().keySet().iterator().next())
                .categorySmall(requestDto.getUpdateTask().get(requestDto.getUpdateTask().keySet().iterator().next()))
                .build();
        taskAbilityRepository.save(taskAbility);
        return new TaskAbilityResponseDto(user);
    }


    //가능 업무는 쉽게 수정 가능하기 때문에 상태값으로 관리X.
    public void deletePossibleTask(Long id) {
        PossibleTask possibleTask = possibleTaskRepository.findById(id)
                .orElseThrow(() -> new BaseException(INVALID_REQUEST));
        possibleTaskRepository.delete(possibleTask);

    }
    //업무역량은 쉽게 수정 가능하기 때문에 상태값으로 관리X.
    public void deleteTaskAbility(Long id) {
        TaskAbility taskAbility = taskAbilityRepository.findById(id)
                .orElseThrow(() -> new BaseException(INVALID_REQUEST));
        taskAbilityRepository.delete(taskAbility);

    }
}
