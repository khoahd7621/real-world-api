package com.khoahd7621.realworldapi.models.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOLoginRequest {
    private String email;
    private String password;
}
