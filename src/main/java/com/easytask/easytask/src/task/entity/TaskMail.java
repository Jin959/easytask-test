package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskMail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskMailId", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User irumi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;

    @Enumerated(EnumType.STRING)
    private MailingStatus mailingStatus = MailingStatus.INVITED;

    public void updateMailingStatusToAgree() {
        this.mailingStatus = MailingStatus.AGREED;
    }

    public enum MailingStatus {
        INVITED, AGREED
    }

    @Builder
    public TaskMail(User irumi, Task task) {
        this.irumi = irumi;
        this.task = task;
    }
}
