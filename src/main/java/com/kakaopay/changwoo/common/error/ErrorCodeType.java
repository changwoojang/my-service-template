package com.kakaopay.changwoo.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCodeType {

    /**
     * 공통 Error
     */
    //공통
    TOKEN_INVALID("T0001", HttpStatus.UNAUTHORIZED, "API Token 필요합니다."),
    JWT_TOKEN_INVALID("T0001", HttpStatus.UNAUTHORIZED, "잘못된 JWT Token 입니다."),
    PASSWORD_INVALID("P0001", HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    MEMBER_ID_INVALID("M0001", HttpStatus.UNAUTHORIZED, "이미 ID의 사용자가 존재합니다."),


    BAD_REQUEST("N9000", HttpStatus.BAD_REQUEST, "잘못된 요청"),
    FORBIDDEN_ACCESS("N9010", HttpStatus.BAD_REQUEST, "해당 정보로 접근이 불가능합니다."),

    //파일 업로드
    FILE_EXTENSION_NOT_FOUND("F0001", HttpStatus.BAD_REQUEST,"잘못된 확장자의 파일입니다."),
    FILE_EMPTY("F0002",HttpStatus.BAD_REQUEST,"파일이 비어있습니다."),

    //은행
    PERIOD_NOT_FOUND("B0001", HttpStatus.BAD_REQUEST,"지원하지 않는 기간 단위입니다."),
    BANK_NOT_FOUND("B0002", HttpStatus.BAD_REQUEST,"존재하지 않는 금융기관입니다.");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    ErrorCodeType(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }
}
