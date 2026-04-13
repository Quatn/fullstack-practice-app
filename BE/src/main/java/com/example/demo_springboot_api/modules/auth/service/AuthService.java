package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.common.errors.InvalidCredentialException;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.common.errors.WrongCredentialException;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder encoder;

  public User login(String loginKey, String password) throws InvalidCredentialException {
    Optional<User> userQuery = userRepository.findByCode(loginKey);

    if (userQuery.isEmpty()) {
      throw new UserNotFoundException("User with provided code does not exists.");
    }

    User user = userQuery.get();
    if (!encoder.matches(password, user.getPassword())) {
      throw new WrongCredentialException("User code or password is incorrect.");
    }

    return user;
  }

  public Boolean checkCodeAvailable(String code) {
    return userRepository.checkCodeAvailable(code);
  }

  public Boolean checkEmailAvailable(String email) {
    return userRepository.checkEmailAvailable(email);
  }
}
