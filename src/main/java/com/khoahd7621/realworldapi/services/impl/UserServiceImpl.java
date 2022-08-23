package com.khoahd7621.realworldapi.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOLoginRequest;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;
import com.khoahd7621.realworldapi.models.user.mapper.UserMapper;
import com.khoahd7621.realworldapi.repositories.UserRepository;
import com.khoahd7621.realworldapi.services.UserService;
import com.khoahd7621.realworldapi.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap) {

        UserDTOLoginRequest userDTOLoginRequest = userLoginRequestMap.get("user");

        Optional<User> userOptional = userRepository.findByEmail(userDTOLoginRequest.getEmail());

        boolean isAuthen = false;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(userDTOLoginRequest.getPassword())) {
                isAuthen = true;
            }
        }
        if (!isAuthen) {
            System.out.println("Email or password is incorrect!");
        }

        Map<String, UserDTOResponse> wrapper = new HashMap<>();
        UserDTOResponse userDTOResponse = UserMapper.toUserDTOResponse(userOptional.get());
        userDTOResponse.setToken(jwtTokenUtil.generateToken(userOptional.get(), 24 * 60 * 60));
        wrapper.put("user", userDTOResponse);

        return wrapper;
    }

}
