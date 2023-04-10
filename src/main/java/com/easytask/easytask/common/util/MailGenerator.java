package com.easytask.easytask.common.util;

import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailGenerator {
    private String recipient;
    private String subject;
    private String body;
    public MailGenerator createMatchingInvitation(Task task, User irumi) {
        this.recipient = irumi.getEmail();
        this.subject = "비지태스크 - 업무 요청";
        this.body = irumi.getName() +
                "이루미님의 도움이 필요한 업무 안내드립니다.<br/>" +
                "<br/>" +
                "선착순으로 자동 매칭되며, 업무 진행이 어렵더라도 매칭 불가 사유를 선택해주시면 다음 매칭률이 올라갑니다.<br/>" +
                "<br/>" +
                "• 업무명 : " +
                task.getTaskName() +
                "<br/>" +
                "• 업무내용 :" +
                task.getDetails() +
                "<br/>" +
                "<br/>" +
                "비지태스크에 접속하여 매칭 여부 결정하기 버튼 클릭하여 더 자세한 내용을 확인하세요.<br/>" +
                "　<br/>" +
                "<br/>" +
                "※ 업무 요청을 신청한 이루미님들에게 전송되는 메시지입니다."
        ;
        return this;
    }

    public MailGenerator createMatchingMail(Task task, User customer) {
        this.recipient = customer.getEmail();
        this.subject = "비지태스크 - 매칭 진행중";
        this.body = customer.getName() +
                "님을 위한 최적의 이루미를 매칭중입니다.<br/>" +
                "<br/>" +
                "업무 진행을 위한 줌 링크는 매칭 완료 후 보내드려요.<br/>" +
                "<br/>" +
                "• 업무명: " +
                task.getTaskName() +
                "<br/>" +
                "※ 매칭 중 일정 변경 시 카카오톡 문의<br/>" +
                "※ 줌를 통해 업무 진행 (얼굴 공개 X)"
        ;

        return this;
    }

    public MailGenerator createMatchedMail(Task task, User customer) {
        this.recipient = customer.getEmail();
        this.subject = "비지태스크 - 매칭 완료";
        this.body = customer.getName() +
                "님<br/>" +
                "<br/>" +
                "업무를 도와드릴 이루미와 매칭 완료됐어요.<br/>" +
                "• 업무 : " +
                task.getTaskName() +
                "<br/>" +
                "<br/>" +
                "※ 일정 변경은 카카오톡 문의 \t\t<br/>" +
                "※ 예약 시간 24시간 전 매칭 취소 시 페널티 발생"
        ;

        return this;
    }

    public MailGenerator createTaskDoneMail(Task task, User customer) {
        this.recipient = customer.getEmail();
        this.subject = "비지태스크 - 업무 종료";
        this.body = customer.getName() +
                "님<br/>" +
                "<br/>" +
                "• 업무명 : " +
                task.getTaskName() +
                "<br/>" +
                "<br/>" +
                "업무 평가를 남겨주시면 다음 매칭에 반영됩니다.<br/>" +
                "　<br/>" +
                "<br/>" +
                "※ 파일 오류 수정은 업무 종료 후 24시간까지 요청 가능<br/>" +
                "※ 고객님의 이지태스크 시간제 사무 대행 서비스 이용에 따라 전송되는 메세지입니다."
        ;

        return this;
    }
}
