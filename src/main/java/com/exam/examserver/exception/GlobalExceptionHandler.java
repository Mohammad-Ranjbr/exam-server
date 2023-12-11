package com.exam.examserver.exception;

import com.exam.examserver.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        String message = resourceNotFoundException.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<ApiResponse> resourceFoundExceptionHandler(ResourceFoundException resourceFoundException){
        String message = resourceFoundException.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginPasswordException.class)
    public ResponseEntity<ApiResponse> loginPasswordExceptionHandler(LoginPasswordException loginPasswordException){
        String message = loginPasswordException.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.UNAUTHORIZED);
    }

}
