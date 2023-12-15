package com.javatechie.aws.common.exception;

public class ApplicationException extends RuntimeException {
    private Integer code = 1000;
    private String message = "An ApplicationException has Occurred: ";

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
