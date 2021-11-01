package com.fpt.myweb.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;

    public AppException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
