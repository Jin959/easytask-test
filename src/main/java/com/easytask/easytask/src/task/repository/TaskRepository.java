package com.easytask.easytask.src.task.repository;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndState(Long taskId, BaseEntity.State active);

    @EntityGraph(attributePaths = {"irumiList"})
    Optional<Task> findWithMappingByIdAndState(Long taskId, BaseEntity.State active);

    @EntityGraph(attributePaths = {"customer"})
    Optional<Task> findWithUserByIdAndState(Long taskId, BaseEntity.State active);

    @EntityGraph(attributePaths = {"irumiList", "customer"})
    Optional<Task> findWithMappingAndCustomerByIdAndState(Long taskId, BaseEntity.State active);

    Optional<Page<Task>> findByCustomerIdAndState(Long customerId, BaseEntity.State active, PageRequest pageRequest);

    Optional<Task> findById(Long id);
}
