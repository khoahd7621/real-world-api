package com.khoahd7621.realworldapi.exceptions.custom;

import com.khoahd7621.realworldapi.models.CustomError;

public class CustomBadRequestException extends BaseCustomException {

    public CustomBadRequestException(CustomError customError) {
        super(customError);
    }

}
