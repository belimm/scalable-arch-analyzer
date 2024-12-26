package com.scalableanalyzer.microservice.order.dto;

public class ErrorDto {
    private Long errorCode;
    private String message;
    private String status;

    public ErrorDto() {
        this.status = "error";
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
