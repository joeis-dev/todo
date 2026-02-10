package com.example.todo.application.usecase;

import com.example.todo.domain.model.Todo;
import com.example.todo.domain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoUseCase {
    private final TodoRepository todoRepository;

    public List<Todo> getTodos(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo createTodo(Todo todo, Long userId) {
        todo.setUserId(userId);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todoUpdates, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        
        todo.setTitle(todoUpdates.getTitle());
        todo.setDescription(todoUpdates.getDescription());
        todo.setCompleted(todoUpdates.isCompleted());
        
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id, Long userId) {
        todoRepository.deleteByIdAndUserId(id, userId);
    }
}
