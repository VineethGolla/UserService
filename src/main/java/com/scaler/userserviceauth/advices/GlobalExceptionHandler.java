package com.scaler.userserviceauth.advices;


import com.scaler.userserviceauth.dtos.ExceptionDto;
import com.scaler.userserviceauth.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> handleInvalidTokenException() {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Invalid Token, TRY WITH CORRECT CREDENTIAL");
        //can add any no. of fields to ExceptionDto like error code, timestamp etc.
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }
}
