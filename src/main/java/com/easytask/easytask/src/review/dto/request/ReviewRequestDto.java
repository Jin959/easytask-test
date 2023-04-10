package com.easytask.easytask.src.review.dto.request;

import com.easytask.easytask.src.review.entity.Review.Recommend;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private Long task;
    private Long irumi;
    private String context;
    private Recommend recommend;

}
