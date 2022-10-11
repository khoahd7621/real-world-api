package com.khoahd7621.realworldapi.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.realworldapi.exceptions.custom.CustomNotFoundException;
import com.khoahd7621.realworldapi.models.CustomError;
import com.khoahd7621.realworldapi.models.profile.dto.ProfileDTOResponse;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOCreate;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException {

        UserDTOLoginRequest userDTOLoginRequest = userLoginRequestMap.get("user");

        Optional<User> userOptional = userRepository.findByEmail(userDTOLoginRequest.getEmail());

        boolean isAuthen = false;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTOLoginRequest.getPassword(), user.getPassword())) {
                isAuthen = true;
            }
        }
        if (!isAuthen) {
            throw new CustomBadRequestException(CustomError.builder()
                    .code("400").message("Email or password is incorrect!").build());
        }

        return buildDTOResponse(userOptional.get());
    }

    @Override
    public Map<String, UserDTOResponse> registerUser(Map<String, UserDTOCreate> userRegisterRequestMap) {
        UserDTOCreate userDTOCreate = userRegisterRequestMap.get("user");

        User user = UserMapper.toUser(userDTOCreate);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return buildDTOResponse(user);
    }

    private Map<String, UserDTOResponse> buildDTOResponse(User user) {
        Map<String, UserDTOResponse> wrapper = new HashMap<>();
        UserDTOResponse userDTOResponse = UserMapper.toUserDTOResponse(user);
        userDTOResponse.setToken(jwtTokenUtil.generateToken(user, 24 * 60 * 60));
        wrapper.put("user", userDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        User userLoggedIn = getUserLoggedIn();
        if (userLoggedIn != null) {
            return buildDTOResponse(userLoggedIn);
        }
        throw new CustomNotFoundException(CustomError.builder()
                .code("404")
                .message("User does not exist")
                .build());
    }

    @Override
    public Map<String, ProfileDTOResponse> getProfile(String username) throws CustomNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException(CustomError.builder()
                    .code("404").message("User does not exist").build());
        }
        User user = userOptional.get();

        User userLoggedIn = getUserLoggedIn();
        boolean isFollowing = false;
        // If user send request is logged in, we need to check that he/she has followed
        // this user or not
        if (userLoggedIn != null) {
            Set<User> followers = user.getFollowers(); // Get all folowers of user that we get profile
            for (User u : followers) {
                if (u.getId() == userLoggedIn.getId()) {
                    isFollowing = true;
                    break;
                }
            }
        }
        return buildProfileDTOResponse(user, isFollowing);
    }

    private User getUserLoggedIn() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return user;
            }
        }
        return null;
    }

    private Map<String, ProfileDTOResponse> buildProfileDTOResponse(User user, boolean isFollowing) {
        Map<String, ProfileDTOResponse> wrapper = new HashMap<>();
        wrapper.put("profile", ProfileDTOResponse.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .following(isFollowing) // Todo: handle later
                .build());
        return wrapper;
    }

    @Override
    public Map<String, ProfileDTOResponse> followUser(String username) throws CustomNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException(CustomError.builder()
                    .code("404").message("User does not exist").build());
        }
        User user = userOptional.get();
        User userLoggedIn = getUserLoggedIn();
        boolean isFollowing = false;
        // If user send request is logged in, we need to check that he/she has followed
        // this user or not
        if (userLoggedIn != null) {
            Set<User> followers = user.getFollowers(); // Get all folowers of user that we get profile
            for (User u : followers) {
                if (u.getId() == userLoggedIn.getId()) {
                    isFollowing = true;
                    break;
                }
            }
        }
        // We only perform the following operation if user logged in are not follow this user
        if (!isFollowing) {
            isFollowing = true;
            user.getFollowers().add(userLoggedIn);
            user = userRepository.save(user);
        }
        return buildProfileDTOResponse(user, isFollowing);
    }

}
