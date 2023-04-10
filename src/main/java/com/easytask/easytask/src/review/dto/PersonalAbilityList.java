package com.easytask.easytask.src.review.dto;

import lombok.Getter;

@Getter
public class PersonalAbilityList {
    private String category;
    private int rating;

    public PersonalAbilityList(String category, int rating) {
        this.category = category;
        this.rating = rating;
    }
}
