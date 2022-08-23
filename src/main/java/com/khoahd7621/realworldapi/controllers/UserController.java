package com.khoahd7621.realworldapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khoahd7621.realworldapi.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOCreate;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOLoginRequest;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;
import com.khoahd7621.realworldapi.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users/login")
    public Map<String, UserDTOResponse> login(@RequestBody Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException {
        return userService.authenticate(userLoginRequestMap);
    }

    @PostMapping(value = "/users")
    public Map<String, UserDTOResponse> registerUser(@RequestBody Map<String, UserDTOCreate> userRegisterRequestMap) {
        return userService.registerUser(userRegisterRequestMap);
    }

}
