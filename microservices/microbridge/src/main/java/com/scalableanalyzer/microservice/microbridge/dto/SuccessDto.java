package com.scalableanalyzer.microservice.microbridge.dto;

public class SuccessDto {
    private String status;
    private String message;

    public SuccessDto() {
        this.status = "success";
    }

    public SuccessDto( String message) {
        this.status = "success";;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
