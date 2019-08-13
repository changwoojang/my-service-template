package com.changwoo.project.common;

import org.springframework.http.HttpStatus;

public enum ErrorCodeType {

    /**
     * 공통 Error
     */
    //공통
    API_KEY_INVALID("C0203", HttpStatus.UNAUTHORIZED, "API Key가 필요합니다."),
    API_KEY_FORBIDDEN("C0204", HttpStatus.FORBIDDEN, "API Key가 비활성화 되었습니다."),


    BAD_REQUEST("N9000", HttpStatus.BAD_REQUEST, "잘못된 요청"),
    FORBIDDEN_ACCESS("N9010", HttpStatus.BAD_REQUEST, "해당 정보로 접근이 불가능합니다."),
    
    //가입자
    USER_NOT_FOUND("N9200", HttpStatus.BAD_REQUEST, "가입자 정보를 찾을 수 없습니다."),
    USER_SMS_NUMBER_MISSING("N9210", HttpStatus.BAD_REQUEST, "핸드폰 번호가 필요합니다."),
    
    //오프라인 쿠폰, 코인
    OFFLINE_NUMBER_NOT_FOUND("N9400", HttpStatus.BAD_REQUEST, "존재하지 않는 응모 번호입니다."),
    OFFLINE_NUNBER_ALREADY_USED("N9410", HttpStatus.BAD_REQUEST, "이미 사용된 응모 번호입니다."),

    //이메일
    USER_EMAIL_FORMAT_INVALID("E0001", HttpStatus.BAD_REQUEST,"이메일 형식이 올바르지 않습니다."),
    
    /**
     * 쿠폰 관련 Error
     */
    // 기준 쿠폰
    BASE_COUPON_NOT_FOUND("N1400", HttpStatus.BAD_REQUEST, "기준 쿠폰 정보를 찾을 수 없습니다."),
    
    // 발급 쿠폰
    ISSU_COUPON_NOT_FOUND("N1500", HttpStatus.BAD_REQUEST, "발급 쿠폰 정보를 찾을 수 없습니다."),
    ISSU_COUPON_EXTRA_NOT_FOUND("N1510", HttpStatus.BAD_REQUEST, "발급 쿠폰 부가 정보가 없습니다."),
    ISSU_COUPON_OUT_OF_DATE("N1610", HttpStatus.BAD_REQUEST, "쿠폰을 지급 할 수 없습니다. (발급 쿠폰이 만료되었거나 지급 가능 기간이 아님)"),
    
    // 지급 쿠폰
    PROVISION_COUPON_NOT_FOUND("N1600", HttpStatus.BAD_REQUEST, "지급된 쿠폰 정보를 찾을 수 없습니다."),
    
    // 쿠폰 지급 요청
    COUPON_PROVISION_REQ_NOT_FOUND("N1620", HttpStatus.BAD_REQUEST, "쿠폰 지급 요청 정보를 찾을 수 없습니다."),
    
    // 쿠폰 사용
    COUPON_USE_NOT_FOUND("N1700", HttpStatus.BAD_REQUEST, "쿠폰 사용 이력이 존재하지 않습니다."),
    PROVISION_COUPON_INVALID("N1710", HttpStatus.BAD_REQUEST, "사용 할 수 없는 쿠폰입니다. (삭제, 사용완료, 취소 또는 유효 기간 만료)"),
    PROVISION_COUPON_EXPIRED("N1720", HttpStatus.BAD_REQUEST, "사용할 수 없는 쿠폰입니다. (유효 기간 만료)"),
    PROVISION_COUPON_PRICE_MISMATCH("N1730", HttpStatus.BAD_REQUEST, "사용할 수 없는 쿠폰입니다. (쿠폰 적용 대상이 쿠폰 적용 가능 가격 범위를 초과함)"),
    PROVISION_COUPON_USER_MISMATCH("N1740", HttpStatus.BAD_REQUEST, "사용할 수 없는 쿠폰입니다. (쿠폰 사용자가 쿠폰 지급 대상자가 아님)"),

    // 오프라인 쿠폰
    OFFLINE_COUPON_REQ_NOT_FOUND("N1800", HttpStatus.BAD_REQUEST, "존재 하지 않는 오프라인 쿠폰 발행번호 입니다."),

    /**
     * 코인 관련 Error
     */
    //기준 코인
    BASE_COIN_NOT_FOUND("N5500", HttpStatus.BAD_REQUEST, "기준 코인 정보를 찾을 수 없습니다."),
    BASE_COIN_EXTRA_NOT_FOUND("N5510", HttpStatus.BAD_REQUEST, "기준 코인 부가 정보를 찾을 수 없습니다."),
    
    //지급 코인
    PROVISION_COIN_NOT_FOUND("N5600", HttpStatus.BAD_REQUEST, "지급된 코인 정보를 찾을 수 없습니다."),
    
    //코인 사용
    COIN_USE_NOT_FOUND("N5700", HttpStatus.BAD_REQUEST, "코인 사용 이력이 존재하지 않습니다."),
    
    INSUFFICIENT_BALANCE("N5710", HttpStatus.BAD_REQUEST, "코인을 사용할 수 없습니다. (잔액 부족)"),

    //오프라인 코인
    OFFLINE_COIN_REQ_NOT_FOUND("N5800", HttpStatus.BAD_REQUEST, "존재 하지 않은 오프라인 코인 발행번호 입니다.")
    ;

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
