package com.example.demo_springboot_api.unit.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.demo_springboot_api.modules.auth.service.ConfiguredUserDetailsService;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import com.example.demo_springboot_api.unit.BaseUnitTest;
import com.example.demo_springboot_api.utils.MockData;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class ConfiguredUserDetailsServiceTest extends BaseUnitTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private ConfiguredUserDetailsService configuredUserDetailsService;

  @Test
  void loadUserByUsername_shouldReturnUserDetails_whenExists() {
    User user = MockData.mockUser();

    when(userRepository.findByCode(user.getCode())).thenReturn(Optional.of(user));

    UserDetails result = configuredUserDetailsService.loadUserByUsername(user.getCode());

    assertEquals(user.getCode(), result.getUsername());
    assertEquals(user.getPassword(), result.getPassword());
  }

  @Test
  void loadUserByUsername_shouldReturnUserDetails_whenNotExists() {
    when(userRepository.findByCode("IMAGINARY_CODE")).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          configuredUserDetailsService.loadUserByUsername("IMAGINARY_CODE");
        });
  }
}
