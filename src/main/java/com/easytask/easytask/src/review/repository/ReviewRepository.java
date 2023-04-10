package com.easytask.easytask.src.review.repository;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.common.BaseEntity.State;
import com.easytask.easytask.src.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager entityManger;
    public void save(Review review) {
        entityManger.persist(review);
    }

    public List<Review> getReviewsByUserIdAndState(Long userId, State state) {
        return entityManger.createQuery("select r from Review r join r.task t join t.customer u where u.id = :userId and r.state = :state", Review.class)
                .setParameter("userId", userId)
                .setParameter("state", state)
                .getResultList();
    }

    public List<Review> getReviewByTaskIdAndIrumiIdAndState(Long taskId, Long irumiId, State state) {
        return entityManger.createQuery("select r from Review r join r.task t join r.irumi u where t.id = :taskId and u.id = :irumiId and r.state = :state", Review.class)
                .setParameter("taskId", taskId)
                .setParameter("irumiId", irumiId)
                .setParameter("state", state)
                .getResultList();
    }

    public List<Review> getReviewsByIrumiIdAndState(Long irumiId, State state) {
        return entityManger.createQuery("select r from Review r join r.irumi u where u.id = :irumiId and r.state = :state", Review.class)
                .setParameter("irumiId", irumiId)
                .setParameter("state", state)
                .getResultList();
    }

    public Review findOne(Long reviewId) {
        return entityManger.find(Review.class, reviewId);
    }



}
