package com.easytask.easytask.src.task.repository;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.TaskMail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskMailRepository extends JpaRepository<TaskMail, Long> {
    @EntityGraph(attributePaths = {"task","irumi"})
    Optional<TaskMail> findWithTaskAndIrumiByTaskIdAndIrumiIdAndState(Long taskId, Long irumiId, BaseEntity.State active);
}
