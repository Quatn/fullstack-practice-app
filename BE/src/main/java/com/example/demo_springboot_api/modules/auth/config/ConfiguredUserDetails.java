package com.example.demo_springboot_api.modules.auth.config;

import com.example.demo_springboot_api.modules.user.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Convert User data entity into UserDetails, which is what Spring Security uses under the hood for
// security protocols
// UserDetails uses a username field and a password field for authentication, those are mapped to
// the code field and password field of the User entity.
public class ConfiguredUserDetails implements UserDetails {

  private User user;
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;

  public ConfiguredUserDetails(User userInfo) {
    this.user = userInfo;
    this.username = userInfo.getCode(); // Use user login code as username
    this.password = userInfo.getPassword();

    String accessPrivileges = userInfo.getAccessPrivileges();
    if (accessPrivileges == null || accessPrivileges.isBlank()) {
      this.authorities = new ArrayList<>();
    } else {
      this.authorities =
          List.of(userInfo.getAccessPrivileges().split(",")).stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
    }
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

  public User getUser() {
    return user;
  }
}
