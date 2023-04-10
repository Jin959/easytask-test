package com.easytask.easytask.common.response;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */


    FIND_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "유저가 없어요"),

    REGISTERED_USER(false, HttpStatus.NOT_FOUND.value(), "이미 가입되어 있는 유저입니다."),
    NOT_VALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(),"이메일 형식에 맞지 않습니다"),
    NOT_FIND_USER(false,HttpStatus.NOT_FOUND.value(),"이메일 또는 비밀번호를 확인해주세요"),
    INVALID_REQUEST(false,HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    INVALID_TOKEN(false,HttpStatus.BAD_REQUEST.value(), "유효하지 않은 토큰입니다."),
    NOT_FOUND_USER(false, HttpStatus.NOT_FOUND.value(), "유저가 존재하지 않습니다."),
    NOT_FOUND_TASK(false, HttpStatus.NOT_FOUND.value(), "업무가 존재하지 않습니다."),
    NOT_FOUND_ABILITY(false, HttpStatus.NOT_FOUND.value(), "해당 역량을 업무 요청서에 등록한 적이 없습니다."),
    NOT_FOUND_MATCHING(false, HttpStatus.NOT_FOUND.value(), "매칭이 존재하지 않거나 매칭이 취소되었습니다."),
    NOT_FOUND_TAKSMAIL(false, HttpStatus.NOT_FOUND.value(), "매칭 메일이 전송된 적이 없거나 취소되었습니다."),
    BAD_REQUEST_DUPLICATE_RELATED_ABILITY(false, HttpStatus.BAD_REQUEST.value(), "이미 등록한 업무관련 역량입니다."),
    BAD_REQUEST_NO_RELATED_ABILITY(false, HttpStatus.BAD_REQUEST.value(), "업무관련 역량을 입력하서야합니다."),
    BAD_REQUEST_NO_TASK_NAME(false, HttpStatus.BAD_REQUEST.value(), "업무 제목을 입력해주세요."),
    BAD_REQUEST_NO_CATEGORY(false, HttpStatus.BAD_REQUEST.value(), "업무 분야를 선택해주세요."),
    BAD_REQUEST_NO_DETAILS(false, HttpStatus.BAD_REQUEST.value(), "업무 내용을 입력해주세요."),
    BAD_REQUEST_ALREADY_QUEUED_MATCHING(false, HttpStatus.BAD_REQUEST.value(), "이미 매칭 신청이 진행 중인 업무입니다."),
    BAD_REQUEST_ALREADY_MATCHED(false, HttpStatus.BAD_REQUEST.value(), "이미 매칭이 종료된 업무입니다."),
    BAD_REQUEST_ALREADY_REVIEW(false, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 리뷰입니다."),

    /**
     * 500
     */
    DB_CONNECTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터 베이스 오류 발생"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다."),
    MAIL_SEND_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "메일전송오류발생"),
    MATCHING_REQUEST_ENROLL_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "매칭 등록 중 에러가 발생했습니다."),
    DYNAMIC_QUERY_CONNECTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "동적쿼리에 대한 데이터 베이스 에러 발생");








    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}