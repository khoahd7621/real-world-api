package com.khoahd7621.realworldapi.models.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPayload {
    private int userId;
    private String email;
}
