package com.khoahd7621.realworldapi.models.user.mapper;

import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;

public class UserMapper {

    public static UserDTOResponse toUserDTOResponse(User user) {
        return UserDTOResponse.builder()
                .email(user.getEmail())
                .token("")
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
