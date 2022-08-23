package com.khoahd7621.realworldapi.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.khoahd7621.realworldapi.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.realworldapi.models.CustomError;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, CustomError> badRequestException(CustomBadRequestException ex) {
        return ex.getErrors();
    }
}
