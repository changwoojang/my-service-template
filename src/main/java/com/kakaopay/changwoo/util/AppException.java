package com.kakaopay.changwoo.util;

import com.kakaopay.changwoo.common.error.ErrorCodeType;
import org.springframework.http.HttpStatus;

/**
 * Created by changwooj111@gmail.com on 2019-08-12
 */
public class AppException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    private String code;

    public AppException(String message, HttpStatus status, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public AppException(ErrorCodeType errorCodeType) {
        this(errorCodeType.getMessage(), errorCodeType.getHttpStatus(), errorCodeType.getCode());
    }

    public AppException(ErrorCodeType errorCodeType, String message) {
        this(message, errorCodeType.getHttpStatus(), errorCodeType.getCode());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
