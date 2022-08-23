package com.khoahd7621.realworldapi.exceptions.custom;

import java.util.HashMap;
import java.util.Map;

import com.khoahd7621.realworldapi.models.CustomError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCustomException extends Exception {
    private Map<String, CustomError> errors;

    public BaseCustomException(CustomError customError) {
        this.errors = new HashMap<>();
        this.errors.put("errors", customError);
    }
}
