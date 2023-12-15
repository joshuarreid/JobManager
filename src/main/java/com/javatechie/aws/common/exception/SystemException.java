package com.javatechie.aws.common.exception;

public class SystemException extends ApplicationException {
    private Integer code = 1200;
    private String message = "A SystemException has Occured: ";

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
