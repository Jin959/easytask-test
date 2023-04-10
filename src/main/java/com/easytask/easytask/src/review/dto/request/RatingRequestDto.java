package com.easytask.easytask.src.review.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RatingRequestDto {
    private List<Long> relatedAbilityList = new ArrayList<>();
    private List<Integer> relatedAbilityRating = new ArrayList<>();

}
