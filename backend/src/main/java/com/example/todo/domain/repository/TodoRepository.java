package com.example.todo.domain.repository;

import com.example.todo.domain.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    List<Todo> findByUserId(Long userId);
    Optional<Todo> findByIdAndUserId(Long id, Long userId);
    Todo save(Todo todo);
    void deleteByIdAndUserId(Long id, Long userId);
}
