package com.khoahd7621.realworldapi.services;

import java.util.Map;

import com.khoahd7621.realworldapi.models.user.dto.UserDTOLoginRequest;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;

public interface UserService {

    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap);

}
