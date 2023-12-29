package com.javatechie.aws.common.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    public static ResponseEntity<Object> generateErrorResponse(Object responseObj, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", responseObj);
        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateErrorResponse(Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", responseObj);
        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }
}


