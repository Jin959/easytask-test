package com.easytask.easytask.src.review.dto;

import lombok.Getter;

@Getter
public class RelatedAbilityList {
    private String category;
    private int rating;

    public RelatedAbilityList(String category, int rating) {
        this.category = category;
        this.rating = rating;
    }
}
