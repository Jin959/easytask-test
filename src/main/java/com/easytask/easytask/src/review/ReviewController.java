package com.easytask.easytask.src.review;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.review.dto.*;
import com.easytask.easytask.src.review.dto.request.PersonalAbilityRequestDto;
import com.easytask.easytask.src.review.dto.request.RatingRequestDto;
import com.easytask.easytask.src.review.dto.request.ReviewRequestDto;
import com.easytask.easytask.src.review.dto.response.PersonalAbilityResponseDto;
import com.easytask.easytask.src.review.dto.response.RatingResponseDto;
import com.easytask.easytask.src.review.dto.response.ReviewResponseDto;
import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(reviewRequestDto);
        return new BaseResponse<>(reviewResponseDto);
    }

    @ResponseBody
    @GetMapping("/lists/users/{userId}")
    public BaseResponse<List<ReviewResponseDto>> getReviewsByUserId(@PathVariable("userId") Long userId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewsByUserId(userId);
        return new BaseResponse<>(reviewList);
    }

    @ResponseBody
    @GetMapping("/lists/irumies/{irumiId}")
    public BaseResponse<List<ReviewResponseDto>> getReviewsByIrumiId(@PathVariable("irumiId") Long irumiId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewsByIrumiId(irumiId);
        return new BaseResponse<>(reviewList);
    }

    @ResponseBody
    @PostMapping("/add/rating/{reviewId}")
    public BaseResponse<ReviewResponseDto> addRatingsOfReview(@PathVariable("reviewId") Long reviewId,
                                                              @RequestBody RatingRequestDto ratingRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.addRatingsOfReview(reviewId, ratingRequestDto);
        return new BaseResponse<>(reviewResponseDto);
    }

    @ResponseBody
    @PostMapping("/personal")
    public BaseResponse<PersonalAbilityResponseDto> createPersonalAbility(@RequestBody PersonalAbilityRequestDto personalAbilityRequestDto) {
       PersonalAbility personalAbility = reviewService.createPersonalAbility(personalAbilityRequestDto);
       PersonalAbilityResponseDto personalAbilityResponseDto = new PersonalAbilityResponseDto(personalAbility);
       return new BaseResponse<>(personalAbilityResponseDto);
    }

    @ResponseBody
    @GetMapping("/average/{reviewId}")
    public BaseResponse<RatingAverageResponseDto> getRatingAverage(@PathVariable("reviewId") Long reviewId) {
        RatingAverageResponseDto ratingAverageResponseDto = reviewService.getRatingAverage(reviewId);
        return new BaseResponse<>(ratingAverageResponseDto);
    }

//    @PatchMapping("/{reviewId}")
//    public BaseResponse<ReviewResponseDto> modifyReview(@PathVariable("reviewId") Long reviewId
//                                                            ,@RequestBody ReviewRequestDto reviewRequestDto) {
//
//    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new BaseResponse("삭제 됐습니다.");
    }
}
