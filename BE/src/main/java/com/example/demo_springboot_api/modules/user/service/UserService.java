package com.example.demo_springboot_api.modules.user.service;

import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder encoder;

  public User addUser(User userInfo) {
    // Encrypt password before saving
    userInfo.setPassword(encoder.encode(userInfo.getPassword()));
    return userRepository.save(userInfo);
  }

  public User findById(Integer id) {
    Optional<User> queryResult = userRepository.findById(id);

    if (queryResult.isEmpty()) {
      throw new UserNotFoundException("User with id: " + id + " not found");
    }

    return queryResult.get();
  }
}
