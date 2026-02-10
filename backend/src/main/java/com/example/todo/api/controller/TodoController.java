package com.example.todo.api.controller;

import com.example.todo.application.usecase.AuthUseCase;
import com.example.todo.application.usecase.TodoUseCase;
import com.example.todo.domain.model.Todo;
import com.example.todo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoUseCase todoUseCase;
    private final AuthUseCase authUseCase;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@AuthenticationPrincipal UserDetails userDetails) {
        User user = authUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(todoUseCase.getTodos(user.getId()));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Todo todo) {
        User user = authUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(todoUseCase.createTodo(todo, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody Todo todo
    ) {
        User user = authUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(todoUseCase.updateTodo(id, todo, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        User user = authUseCase.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        todoUseCase.deleteTodo(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
