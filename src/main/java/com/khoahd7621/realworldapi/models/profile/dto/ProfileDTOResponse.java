package com.khoahd7621.realworldapi.models.profile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDTOResponse {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
