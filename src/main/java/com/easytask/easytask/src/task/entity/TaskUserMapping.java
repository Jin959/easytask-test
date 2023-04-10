package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.user.entity.User;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskUserMapping extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "taskUserMappingId", nullable = false, updatable = false)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User irumi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;
    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus = ProgressStatus.STANDBY;

    public void updateProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public enum ProgressStatus {
        STANDBY, DOING, DONE
    }
    @Builder
    public TaskUserMapping(User irumi, Task task) {
        this.irumi = irumi;
        this.task = task;
    }
}
