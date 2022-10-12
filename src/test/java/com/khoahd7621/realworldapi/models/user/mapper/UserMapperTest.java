package com.khoahd7621.realworldapi.models.user.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOCreate;
import com.khoahd7621.realworldapi.models.user.dto.UserDTOResponse;

public class UserMapperTest {
    @Test
    void testToUserDTOResponse() {
        // Given
        User user = User.builder()
                            .email("khoahd@gmail.com")
                            .username("khoa")
                            .bio("Hello")
                            .image("url_image.com").build();
        UserDTOResponse expect = UserDTOResponse.builder()
                                    .email("khoahd@gmail.com")
                                    .username("khoa")
                                    .token("")
                                    .bio("Hello")
                                    .image("url_image.com").build();
        // When
        UserDTOResponse actual = UserMapper.toUserDTOResponse(user);
        // Then
        assertEquals(expect, actual);
    }

    @Test
    void testToUser() {
        // Given
        UserDTOCreate userDTOCreate = UserDTOCreate.builder()
                                            .username("username")
                                            .email("email")
                                            .password("password").build();
        User expected = User.builder()
                            .username("username")
                            .email("email")
                            .password("password").build();
        // When
        User actual = UserMapper.toUser(userDTOCreate);
        // Then
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }
}
