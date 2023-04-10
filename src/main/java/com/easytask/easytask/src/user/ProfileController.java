package com.easytask.easytask.src.user;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.user.dto.request.TaskRequestDto;
import com.easytask.easytask.src.user.dto.response.PossibleTaskResponseDto;
import com.easytask.easytask.src.user.dto.response.TaskAbilityResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easytask/user")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/possible/{email}")
    public BaseResponse<PossibleTaskResponseDto> getPossibleTask(
            @PathVariable String email) {
        PossibleTaskResponseDto possibleTaskResponseDto = profileService.getPossibleTask(email);
        return new BaseResponse<>(possibleTaskResponseDto);
    }

    @GetMapping("/ability/{email}")
    public BaseResponse<TaskAbilityResponseDto> getTaskAbility(
            @PathVariable String email) {
        TaskAbilityResponseDto taskAbilityResponseDto = profileService.getTaskAbility(email);
        return new BaseResponse<>(taskAbilityResponseDto);
    }

    @PatchMapping("/possible/{email}")
    public BaseResponse<PossibleTaskResponseDto> addPossibleTask(
            @RequestBody TaskRequestDto requestDto, @PathVariable String email){

        PossibleTaskResponseDto possibleTaskResponseDto = profileService.addPossibleTask(requestDto,email);
        return new BaseResponse<>(possibleTaskResponseDto);
    }

    @PatchMapping("/ability/{email}")
    public BaseResponse<TaskAbilityResponseDto> addTaskAbility(
            @RequestBody TaskRequestDto requestDto, @PathVariable String email){

        TaskAbilityResponseDto taskAbilityResponseDto = profileService.addTaskAbility(requestDto,email);
        return new BaseResponse<>(taskAbilityResponseDto);
    }

    @DeleteMapping("/possible/{id}")
    public BaseResponse<String> deletePossibleTask(@PathVariable Long id){
        profileService.deletePossibleTask(id);
        return new BaseResponse<>("삭제완료");

    }

    @DeleteMapping("/ability/{id}")
    public BaseResponse<String> deleteTaskAbility(@PathVariable Long id){
        profileService.deleteTaskAbility(id);
        return new BaseResponse<>("삭제완료");
    }
}
