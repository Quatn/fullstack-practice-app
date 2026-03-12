package com.example.demo_springboot_api.modules.user.repository;

import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.dsl.IUserDSLRepository;
import java.util.Optional;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository
    extends CrudRepository<User, Integer>, QuerydslPredicateExecutor<User>, IUserDSLRepository {
  Optional<User> findByCode(String code);

  Optional<User> findByEmail(String email);
}
