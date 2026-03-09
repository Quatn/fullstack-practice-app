package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
  @Autowired private UserRepository userRepository;

  public User login(String loginKey, String password) {
    Optional<User> userQuery = userRepository.findByEmail(loginKey);

    if (userQuery.isEmpty()) {
      throw new UserNotFoundException("User with provided email does not exists.");
    }

    return userQuery.get();
  }
}
