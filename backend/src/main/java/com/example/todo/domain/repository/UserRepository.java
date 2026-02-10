package com.example.todo.domain.repository;

import com.example.todo.domain.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderAndOauthId(String provider, String oauthId);
    User save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
