package com.khoahd7621.realworldapi.exceptions.custom;

import com.khoahd7621.realworldapi.models.CustomError;

public class CustomNotFoundException extends BaseCustomException {

    public CustomNotFoundException(CustomError customError) {
        super(customError);
    }

}
