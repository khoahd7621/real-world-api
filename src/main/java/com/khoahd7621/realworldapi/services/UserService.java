package com.khoahd7621.realworldapi.services;

import java.util.Map;

import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.realworldapi.exceptions.custom.CustomNotFoundException;
import com.khoahd7621.realworldapi.models.profile.dto.ProfileDTOResponse;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOCreate;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOLoginRequest;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOUpdateRequest;

public interface UserService {

    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException;

    public Map<String, UserDTOResponse> registerUser(Map<String, UserDTOCreate> userRegisterRequestMap);

    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException;

    public Map<String, ProfileDTOResponse> getProfile(String username) throws CustomNotFoundException;

    public Map<String, ProfileDTOResponse> followUser(String username) throws CustomNotFoundException;

    public Map<String, ProfileDTOResponse> unFollowUser(String username) throws CustomNotFoundException;

    public Map<String, UserDTOResponse> updateUser(Map<String, UserDTOUpdateRequest> userUpdateRequestMap);

    public User getUserLoggedIn();
}
