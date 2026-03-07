package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ConfiguredUserDetailsService implements UserDetailsService {

  private final UserRepository repository;
  private final PasswordEncoder encoder;

  @Autowired
  public ConfiguredUserDetailsService(UserRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  // Method to load user details by username (email)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Fetch user from the database by email (username)
    Optional<User> userInfo = repository.findByEmail(username);

    if (userInfo.isEmpty()) {
      throw new UsernameNotFoundException("User not found with email: " + username);
    }

    // Convert UserInfo to UserDetails (UserInfoDetails)
    return new ConfiguredUserDetails(userInfo.get());
  }

  // Add any additional methods for registering or managing users
  public String addUser(User userInfo) {
    // Encrypt password before saving
    userInfo.setPassword(encoder.encode(userInfo.getPassword()));
    repository.save(userInfo);
    return "User added successfully!";
  }
}
