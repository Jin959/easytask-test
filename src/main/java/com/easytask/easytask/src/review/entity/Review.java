package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User irumi;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratingId")
    private Rating rating;
    private String context;
    @Enumerated(EnumType.STRING)
    private Recommend recommend;

    @Builder
    public Review(Task task, User irumi, Rating rating, String context, Recommend recommend) {
        this.task = task;
        this.irumi = irumi;
        this.rating = rating;
        this.context = context;
        this.recommend = recommend;
    }

    public void addRating(Rating rating) {
        this.rating = rating;
    }

    public void delete() {
        this.state = State.INACTIVE;
    }

    public enum Recommend {
        RECOMMEND, NOTRECOMMEND
    }

}
