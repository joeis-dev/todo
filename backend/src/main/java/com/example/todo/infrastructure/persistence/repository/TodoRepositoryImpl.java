package com.example.todo.infrastructure.persistence.repository;

import com.example.todo.domain.model.Todo;
import com.example.todo.domain.repository.TodoRepository;
import com.example.todo.infrastructure.persistence.entity.TodoEntity;
import com.example.todo.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {
    private final JpaTodoRepository jpaTodoRepository;

    @Override
    public List<Todo> findByUserId(Long userId) {
        return jpaTodoRepository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findByIdAndUserId(Long id, Long userId) {
        return jpaTodoRepository.findByIdAndUserId(id, userId).map(this::toDomain);
    }

    @Override
    public Todo save(Todo todo) {
        TodoEntity entity = toEntity(todo);
        return toDomain(jpaTodoRepository.save(entity));
    }

    @Override
    public void deleteByIdAndUserId(Long id, Long userId) {
        jpaTodoRepository.deleteByIdAndUserId(id, userId);
    }

    private Todo toDomain(TodoEntity entity) {
        return Todo.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .completed(entity.isCompleted())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .userId(entity.getUser().getId())
                .build();
    }

    private TodoEntity toEntity(Todo todo) {
        return TodoEntity.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .user(UserEntity.builder().id(todo.getUserId()).build())
                .build();
    }
}
