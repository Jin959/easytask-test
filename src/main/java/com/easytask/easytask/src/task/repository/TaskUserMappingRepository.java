package com.easytask.easytask.src.task.repository;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskUserMappingRepository extends JpaRepository<TaskUserMapping, Long> {

    List<TaskUserMapping> findAllByTaskIdAndState(Long taskId, BaseEntity.State active);

    @EntityGraph(attributePaths = {"task"})
    Optional<Page<TaskUserMapping>> findWithTaskByIrumiIdAndState(Long irumiId, BaseEntity.State active, PageRequest pageRequest);

    Optional<TaskUserMapping> findById(Long id);
}
