package com.example.todo.application.usecase;

import com.example.todo.application.dto.RegisterRequest;
import com.example.todo.domain.model.User;
import com.example.todo.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthUseCase authUseCase;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
    }

    @Test
    void register_Success() {
        // Arrange
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = authUseCase.register(registerRequest);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository).save(any());
    }

    @Test
    void register_UsernameExists_ThrowsException() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authUseCase.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void findByUsername_Success() {
        // Arrange
        User user = User.builder().username("testuser").build();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = authUseCase.findByUsername("testuser");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }
}
