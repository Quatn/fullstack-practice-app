package com.example.demo_springboot_api.modules.auth.repository;

import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AuthSessionRepository extends CrudRepository<AuthSession, Long> {
  Optional<AuthSession> findByUserId(Long userId);

  Optional<AuthSession> findByUuid(String uuid);
}
