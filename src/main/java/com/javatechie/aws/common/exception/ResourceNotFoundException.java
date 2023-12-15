package com.javatechie.aws.common.exception;

public class ResourceNotFoundException extends ApplicationException {
    private Integer code = 1600;
    private String message = "Resource Does not Exist";

    public ResourceNotFoundException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResourceNotFoundException(Integer code) {
        this.code = code;
    }

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

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
