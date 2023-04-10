package com.easytask.easytask.src.task;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.task.dto.request.RelatedAbilityRequestDto;
import com.easytask.easytask.src.task.dto.response.RelatedAbilityResponseDto;
import com.easytask.easytask.src.task.dto.response.TaskResponseDto;
import com.easytask.easytask.src.task.dto.request.TaskRequestDto;
import com.easytask.easytask.src.task.dto.response.TaskIdResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/easytask/tasks")
public class TaskController {

    private final TaskService taskService;

    @ResponseBody
    @PostMapping("/customers/{customerId}")
    public BaseResponse<TaskIdResponseDto> createTask(
            @PathVariable("customerId") Long customerId, @RequestBody TaskRequestDto taskRequestDto) {
        TaskIdResponseDto taskIdResponseDto = taskService.createTask(customerId, taskRequestDto);
        return new BaseResponse<>(taskIdResponseDto);
    }

    @ResponseBody
    @GetMapping("{taskId}")
    public BaseResponse<TaskResponseDto> getTask(@PathVariable("taskId") Long taskId) {
        TaskResponseDto taskResponseDto = taskService.getTask(taskId);
        return new BaseResponse<>(taskResponseDto);
    }

    @ResponseBody
    @PutMapping("{taskId}")
    public BaseResponse<TaskIdResponseDto> updateTask(
            @PathVariable("taskId") Long taskId, @RequestBody TaskRequestDto taskRequestDto) {
        TaskIdResponseDto taskIdResponseDto = taskService.updateTask(taskId, taskRequestDto);
        return new BaseResponse<>(taskIdResponseDto);
    }

    @ResponseBody
    @DeleteMapping("{taskId}")
    public BaseResponse<String> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return new BaseResponse<>("업무가 삭제되었습니다.");
    }

    @ResponseBody
    @PostMapping("{taskId}/related-abilities")
    public BaseResponse<RelatedAbilityResponseDto> postRelatedAbility(
            @PathVariable("taskId") Long taskId, @RequestBody RelatedAbilityRequestDto relatedAbilityRequestDto) {
        RelatedAbilityResponseDto relatedAbilityResponseDto = taskService.postRelatedAbility(taskId, relatedAbilityRequestDto);
        return new BaseResponse<>(relatedAbilityResponseDto);
    }

    @ResponseBody
    @DeleteMapping("related-abilities/{relatedAbilityId}")
    public BaseResponse<String> deleteRelatedAbility(@PathVariable("relatedAbilityId") Long relatedAbilityId) {
        taskService.deleteRelatedAbility(relatedAbilityId);
        return new BaseResponse<>("업무 관련 역량에서 제외 되었습니다.");
    }

    @ResponseBody
    @PatchMapping("{taskId}/customers/matching")
    public BaseResponse<String> updateTaskToMatching(@PathVariable("taskId") Long taskId) {
        taskService.updateTaskToMatching(taskId);
        return new BaseResponse<>("매칭 요청이 등록되었습니다.");
    }

    @ResponseBody
    @PatchMapping("{taskId}/irumies/{irumiId}/accept")
    public BaseResponse<String> updateTaskToMatched(
            @PathVariable("taskId") Long taskId, @PathVariable("irumiId") Long irumiId) {
        taskService.updateTaskToMatched(taskId, irumiId);
        return new BaseResponse<>("해당 업무와 매칭 되었습니다.");
    }

    @ResponseBody
    @PatchMapping("{taskId}/irumies/{irumiId}/doing")
    public BaseResponse<String> updateTaskToDoing(
            @PathVariable("taskId") Long taskId, @PathVariable("irumiId") Long irumiId) {
        taskService.updateTaskToDoing(taskId, irumiId);
        return new BaseResponse<>("업무를 시작했습니다.");
    }

    @ResponseBody
    @PatchMapping("{taskId}/irumies/{irumiId}/done")
    public BaseResponse<String> updateTaskToDone(
            @PathVariable("taskId") Long taskId, @PathVariable("irumiId") Long irumiId) {
        taskService.updateTaskToDone(taskId, irumiId);
        return new BaseResponse<>("업무를 완료했습니다.");
    }

    @ResponseBody
    @GetMapping("/customers/{customerId}/mytask")
    public BaseResponse<List<TaskResponseDto>> getCustomerMyTaskList(
            @PathVariable("customerId") Long customerId, @RequestParam Integer page, @RequestParam Integer size) {
        List<TaskResponseDto> taskResponseDtoList = taskService.getCustomerMyTaskList(customerId, page, size);
        return new BaseResponse<>(taskResponseDtoList);
    }

    @ResponseBody
    @GetMapping("/irumies/{irumiId}/mytask")
    public BaseResponse<List<TaskResponseDto>> getIrumiMyTaskList(
            @PathVariable("irumiId") Long irumiId, @RequestParam Integer page, @RequestParam Integer size) {
        List<TaskResponseDto> taskPageResponseDto = taskService.getIrumiMyTaskList(irumiId, page, size);
        return new BaseResponse<>(taskPageResponseDto);
    }
}
