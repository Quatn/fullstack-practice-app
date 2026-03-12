package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Bridges UserService (which does the normal data operation), with UserDetailsService, which
// provides user data for Spring Security.
// UserDetailsService uses UserDetails class, which have a username field and a password field that
// corresponse to some fields on the User entity.
@Service
public class ConfiguredUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository repository;

  // Method to load user details by username
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Fetch user from the database by user login code
    Optional<User> userInfo = repository.findByCode(username);

    if (userInfo.isEmpty()) {
      throw new UsernameNotFoundException("User not found with email: " + username);
    }

    // Convert UserInfo to UserDetails (UserInfoDetails)
    return new ConfiguredUserDetails(userInfo.get());
  }
}
