package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.modules.user.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ConfiguredUserDetails implements UserDetails {

  private String username; // Changed from 'name' to 'email' for clarity
  private String password;
  private List<GrantedAuthority> authorities;

  public ConfiguredUserDetails(User userInfo) {
    this.username = userInfo.getEmail(); // Use email as username
    this.password = userInfo.getPassword();
    this.authorities =
        List.of(userInfo.getAccessPrivileges().split(",")).stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getPassword() {
    return password;
  }
}
