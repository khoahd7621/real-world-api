package com.khoahd7621.realworldapi.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOLoginRequest;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;
import com.khoahd7621.realworldapi.repositories.UserRepository;
import com.khoahd7621.realworldapi.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void testAuthenticate_success() throws CustomBadRequestException {
        // Given
        UserDTOLoginRequest userDTOLoginRequest = UserDTOLoginRequest.builder().email("email").password("password").build();
        Map<String, UserDTOLoginRequest> userLoginRequestMap = new HashMap<>();
        userLoginRequestMap.put("user", userDTOLoginRequest);
        Map<String, UserDTOResponse> expected = new HashMap<>();
        UserDTOResponse userDTOResponseExpected = UserDTOResponse.builder().email("email").username("username").bio("bio").image("image").token("token").build();
        expected.put("user", userDTOResponseExpected);
        // When
        Optional<User> userOptional = Optional.of(User.builder().id(1).email("email").username("username").bio("bio").image("image").password("password").build());
        when(userRepository.findByEmail(userDTOLoginRequest.getEmail())).thenReturn(userOptional);
        when(passwordEncoder.matches(userDTOLoginRequest.getPassword(), userOptional.get().getPassword())).thenReturn(true);
        when(jwtTokenUtil.generateToken(userOptional.get(), 24 * 60 * 60)).thenReturn("token");
        Map<String, UserDTOResponse> actual = userServiceImpl.authenticate(userLoginRequestMap);
        // Then
        assertEquals(expected.containsKey("user"), actual.containsKey("user"));
        UserDTOResponse userDTOResponseActual = actual.get("user");
        assertEquals(userDTOResponseExpected.getEmail(), userDTOResponseActual.getEmail());
        assertEquals(userDTOResponseExpected.getUsername(), userDTOResponseActual.getUsername());
        assertEquals(userDTOResponseExpected.getBio(), userDTOResponseActual.getBio());
        assertEquals(userDTOResponseExpected.getImage(), userDTOResponseActual.getImage());
        assertEquals(userDTOResponseExpected.getToken(), userDTOResponseActual.getToken());
    }

    @Test
    void testAuthenticate_throw_CustomBadRequestException() {
        // Given
        UserDTOLoginRequest userDTOLoginRequest = 
            UserDTOLoginRequest.builder()
                .email("email")
                .password("password").build();
        Map<String, UserDTOLoginRequest> userLoginRequestMap = new HashMap<>();
        userLoginRequestMap.put("user", userDTOLoginRequest);
        // When
        when(userRepository.findByEmail(userDTOLoginRequest.getEmail())).thenReturn(Optional.ofNullable(null));
        // Then
        assertThrows(CustomBadRequestException.class, () -> {
            userServiceImpl.authenticate(userLoginRequestMap);
        });
    }

    @Test
    void testFollowUser() {

    }

    @Test
    void testGetCurrentUser() {

    }

    @Test
    void testGetProfile() {

    }

    @Test
    void testGetUserLoggedIn() {

    }

    @Test
    void testRegisterUser() {

    }

    @Test
    void testUnFollowUser() {

    }

    @Test
    void testUpdateUser() {

    }
}
