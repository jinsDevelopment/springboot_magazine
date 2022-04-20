package com.sparta.magazine.advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Map<String, Object>> handler(RestException e) {
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("msg", e.getMessage());

        return new ResponseEntity<>(resBody, e.getHttpStatus());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        String errorMessage = e.getBindingResult()
//            .getAllErrors()
//            .get(0)
//            .getDefaultMessage();
//
//        printExceptionMessage(errorMessage);
//        return new ResponseEntity<>(new BaseResult.Normal(INVALID_PARAMETER), HttpStatus.BAD_REQUEST);
//    }
}
