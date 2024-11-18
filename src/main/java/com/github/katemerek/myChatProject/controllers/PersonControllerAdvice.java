package com.github.katemerek.myChatProject.controllers;

import com.github.katemerek.FirstSecurityApp.util.ErrorResponse;
import com.github.katemerek.FirstSecurityApp.util.PersonNotCreated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PersonControllerAdvice {
    @ExceptionHandler(PersonNotCreated.class)
    public ResponseEntity<ErrorResponse> handlePersonNotCreatedException(PersonNotCreated e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), System.currentTimeMillis()) {
                });
    }
}
