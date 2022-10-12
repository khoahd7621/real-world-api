package com.khoahd7621.realworldapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.khoahd7621.realworldapi.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOLoginRequest;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;
import com.khoahd7621.realworldapi.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void testGetCurrentUser() {
        
    }

    @Test
    void testLogin() throws CustomBadRequestException {
        // Given
        Map<String, UserDTOLoginRequest> userLoginRequestMap = new HashMap<>();
        
        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
            .email("email").token("token").bio("bio").username("username").image("image").build();
        Map<String, UserDTOResponse> userLoginResponseMap = new HashMap<>();
        userLoginResponseMap.put("user", userDTOResponse);
        // When
        when(userService.authenticate(userLoginRequestMap)).thenReturn(userLoginResponseMap);
        Map<String, UserDTOResponse> actual = userController.login(userLoginRequestMap);
        // Then
        assertEquals(userLoginResponseMap.containsKey("user"), actual.containsKey("user"));
        assertEquals(userLoginResponseMap.get("user").getEmail(), actual.get("user").getEmail());
        assertEquals(userLoginResponseMap.get("user").getBio(), actual.get("user").getBio());
        assertEquals(userLoginResponseMap.get("user").getImage(), actual.get("user").getImage());
        assertEquals(userLoginResponseMap.get("user").getToken(), actual.get("user").getToken());
        assertEquals(userLoginResponseMap.get("user").getUsername(), actual.get("user").getUsername());
        // => Test controller like this don't have any meaning. => Keywords: Test Controller with mockMVC -> Test JSON return
    }

    @Test
    void testRegisterUser() {

    }

    @Test
    void testUpdateUser() {

    }
}
