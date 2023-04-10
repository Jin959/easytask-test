package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.dto.response.PersonalAbilityRatingResponseDto;
import com.easytask.easytask.src.review.dto.response.PersonalAbilityResponseDto;
import com.easytask.easytask.src.review.dto.response.RatingResponseDto;
import com.easytask.easytask.src.review.dto.response.RelatedAbilityRatingResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingAverageResponseDto {
    private String relatedAbilitly = "전문 역량";
    private double relatedAverage;
    private List<RelatedAbilityRatingResponseDto> getRelatedAbilityResponseDtolist;
    private String personalAbility = "개인 역량";
    private double personalAverage;
    private List<PersonalAbilityRatingResponseDto> getPersonalAbilityResponseDtolist;

    public RatingAverageResponseDto(RatingResponseDto ratingResponseDto, double relatedAverage, double personalAverage) {
        this.getRelatedAbilityResponseDtolist = ratingResponseDto.getRelatedAbilityRatingResponseDtoList();
        this.getPersonalAbilityResponseDtolist = ratingResponseDto.getPersonalAbilityRatingResponseDtoList();
        this.relatedAverage = relatedAverage;
        this.personalAverage = personalAverage;
    }


}
