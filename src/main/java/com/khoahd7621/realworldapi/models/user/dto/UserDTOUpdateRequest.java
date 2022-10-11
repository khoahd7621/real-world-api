package com.khoahd7621.realworldapi.models.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOUpdateRequest {
    private String email;
    private String bio;
    private String image;
}
