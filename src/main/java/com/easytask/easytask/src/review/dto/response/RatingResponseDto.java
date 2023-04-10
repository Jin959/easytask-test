package com.easytask.easytask.src.review.dto.response;

import com.easytask.easytask.src.review.entity.PersonalAbilityRating;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.RelatedAbilityRating;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RatingResponseDto {
    private List<RelatedAbilityRatingResponseDto> relatedAbilityRatingResponseDtoList = new ArrayList<>();
    private List<PersonalAbilityRatingResponseDto> personalAbilityRatingResponseDtoList = new ArrayList<>();

    public void addRelatedAbilityRatingResponseDto(RelatedAbilityRatingResponseDto relatedAbilityRatingResponseDto) {
        this.relatedAbilityRatingResponseDtoList.add(relatedAbilityRatingResponseDto);
    }

    public void addPersonalAbilityRatingResponseDto(PersonalAbilityRatingResponseDto personalAbilityRatingResponseDto) {
        this.personalAbilityRatingResponseDtoList.add(personalAbilityRatingResponseDto);
    }

    public RatingResponseDto(Rating rating) {
        for (RelatedAbilityRating relatedAbilityRating : rating.getRelatedAbilityRatingList()) {
            RelatedAbilityRatingResponseDto relatedAbilityRatingResponseDto = new RelatedAbilityRatingResponseDto(relatedAbilityRating);
            this.relatedAbilityRatingResponseDtoList.add(relatedAbilityRatingResponseDto);
        }

        for (PersonalAbilityRating personalAbilityRating : rating.getPersonalAbilityRatingList()) {
            PersonalAbilityRatingResponseDto personalAbilityRatingResponseDto = new PersonalAbilityRatingResponseDto(personalAbilityRating);
            this.personalAbilityRatingResponseDtoList.add(personalAbilityRatingResponseDto);
        }
    }

}
