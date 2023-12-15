package com.javatechie.aws.common.utility;

import com.javatechie.aws.common.exception.ApplicationException;
import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.common.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleRestException(Exception ex, WebRequest request) {
        logger.error(ex);
        HttpStatus status;
        if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            Error error = new Error();
            ApplicationException exception = (ApplicationException) ex;
            error.setCode(exception.getCode());
            error.setMessage(exception.getMessage());
            return ResponseHandler.generateErrorResponse(error, status);
        }

        else if (ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.BAD_REQUEST;
            Error error = new Error();
            DataIntegrityViolationException exception = (DataIntegrityViolationException) ex;
            error.setMessage("A NonNull field is Null");
            error.setDetailedMessage(exception.getMessage());
            return ResponseHandler.generateErrorResponse(error, status);

        }

        else {
            Error error = new Error();
            error.setCode(2000);
            error.setMessage(ex.getMessage());
            return ResponseHandler.generateErrorResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    class Error {
        private Integer code;
        private String message;
        private String detailedMessage;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDetailedMessage() {
            return detailedMessage;
        }

        public void setDetailedMessage(String detailedMessage) {
            this.detailedMessage = detailedMessage;
        }
    }

}
