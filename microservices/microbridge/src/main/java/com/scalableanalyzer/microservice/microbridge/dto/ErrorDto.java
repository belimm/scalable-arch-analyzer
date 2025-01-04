package com.scalableanalyzer.microservice.microbridge.dto;

public class ErrorDto {
    private Long errorCode;
    private String message;


    public ErrorDto() {
    }

    public ErrorDto(Long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
