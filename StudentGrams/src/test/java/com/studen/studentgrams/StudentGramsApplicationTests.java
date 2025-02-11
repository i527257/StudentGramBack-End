package com.studen.studentgrams;

import com.studen.studentgrams.Features.user.User;
import com.studen.studentgrams.Features.user.Authentication.AuthenticationResponse;
import com.studen.studentgrams.Features.user.Authentication.dto.AuthenticationRequest;
import com.studen.studentgrams.Features.user.Authentication.dto.RegisterRequest;
import com.studen.studentgrams.Features.user.Request.UserResponse;
import com.studen.studentgrams.Config.JWTService;
import com.studen.studentgrams.Features.user.UserRepository;
import com.studen.studentgrams.Features.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private User mockUser;
    private User updatedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .displayname("john_doe")
                .admin(false)
                .profilePicture(new byte[0])
                .build();

        updatedUser = User.builder()
                .firstname("Jane")
                .lastname("Doe")
                .email("jane.doe@example.com")
                .password("newpassword")
                .displayname("jane_doe")
                .admin(true)
                .profilePicture(new byte[0])
                .build();
    }

    @Test
    void testGetUserProfileById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        UserResponse userResponse = userService.getUserProfileById(1L);
        assertNotNull(userResponse);
        assertEquals("john_doe", userResponse.displayname());
        assertEquals("john.doe@example.com", userResponse.email());
    }

    @Test
    void testGetUserProfileById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            userService.getUserProfileById(1L);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        // Mock the behavior of userRepository
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.UpdateUser(1L, updatedUser);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("Jane", mockUser.getFirstname());
        assertEquals("Doe", mockUser.getLastname());
        assertEquals("jane.doe@example.com", mockUser.getEmail());
        assertEquals("jane_doe", mockUser.getDisplayname());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            userService.UpdateUser(1L, updatedUser);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }
}
