package com.example.todo.application.usecase;

import com.example.todo.domain.model.Todo;
import com.example.todo.domain.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoUseCaseTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoUseCase todoUseCase;

    @Test
    void getTodos_ReturnsList() {
        // Arrange
        List<Todo> todos = List.of(Todo.builder().id(1L).title("Task 1").build());
        when(todoRepository.findByUserId(1L)).thenReturn(todos);

        // Act
        List<Todo> result = todoUseCase.getTodos(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
    }

    @Test
    void createTodo_Success() {
        // Arrange
        Todo todo = Todo.builder().title("New Task").build();
        when(todoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Todo result = todoUseCase.createTodo(todo, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(todoRepository).save(any());
    }

    @Test
    void updateTodo_Success() {
        // Arrange
        Todo existingTodo = Todo.builder().id(1L).userId(1L).title("Old Title").build();
        Todo updates = Todo.builder().title("New Title").completed(true).build();
        
        when(todoRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Todo result = todoUseCase.updateTodo(1L, updates, 1L);

        // Assert
        assertEquals("New Title", result.getTitle());
        assertTrue(result.isCompleted());
    }

    @Test
    void deleteTodo_Success() {
        // Act
        todoUseCase.deleteTodo(1L, 1L);

        // Assert
        verify(todoRepository).deleteByIdAndUserId(1L, 1L);
    }
}
