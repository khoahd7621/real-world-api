package com.khoahd7621.realworldapi.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khoahd7621.realworldapi.exceptions.custom.CustomNotFoundException;
import com.khoahd7621.realworldapi.models.profile.dto.ProfileDTOResponse;
import com.khoahd7621.realworldapi.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {
    private final UserService userService;

    @GetMapping(value = "/{username}")
    public Map<String, ProfileDTOResponse> getProfile(@PathVariable String username) throws CustomNotFoundException {
        return userService.getProfile(username);
    }

}
