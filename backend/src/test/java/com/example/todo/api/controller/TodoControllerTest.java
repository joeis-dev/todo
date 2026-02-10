package com.example.todo.api.controller;

import com.example.todo.application.usecase.AuthUseCase;
import com.example.todo.application.usecase.TodoUseCase;
import com.example.todo.domain.model.Todo;
import com.example.todo.domain.model.User;
import com.example.todo.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable filters for simpler controller testing if needed, or use WithMockUser
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoUseCase todoUseCase;

    @MockBean
    private AuthUseCase authUseCase;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @MockBean
    private com.example.todo.infrastructure.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private org.springframework.security.authentication.AuthenticationProvider authenticationProvider;

    @Test
    @WithMockUser(username = "testuser")
    void getTodos_ReturnsList() throws Exception {
        // Arrange
        User user = User.builder().id(1L).username("testuser").build();
        when(authUseCase.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(todoUseCase.getTodos(1L)).thenReturn(List.of(Todo.builder().id(1L).title("Task 1").build()));

        // Act & Assert
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void createTodo_Success() throws Exception {
        // Arrange
        User user = User.builder().id(1L).username("testuser").build();
        Todo todo = Todo.builder().id(1L).title("New Task").build();
        
        when(authUseCase.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(todoUseCase.createTodo(any(), eq(1L))).thenReturn(todo);

        // Act & Assert
        mockMvc.perform(post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"New Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Task"));
    }
}
