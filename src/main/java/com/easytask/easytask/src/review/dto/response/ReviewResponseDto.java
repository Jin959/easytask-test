package com.easytask.easytask.src.review.dto.response;

import com.easytask.easytask.src.review.entity.Review.Recommend;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.task.dto.response.TaskResponseDto;
import com.easytask.easytask.src.user.dto.response.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private TaskResponseDto task;
    private UserResponseDto irumi;
    private RatingResponseDto rating;
    private String context;
    private Recommend recommend;

    public ReviewResponseDto(Review review) {
        this.task = new TaskResponseDto(review.getTask());
        this.irumi = new UserResponseDto(review.getIrumi());
        this.context = review.getContext();
        this.recommend = review.getRecommend();
    }

    public void addRatingResponseDto(RatingResponseDto ratingResponseDto) {
        this.rating = ratingResponseDto;
    }

}
