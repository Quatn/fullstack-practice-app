package com.example.demo_springboot_api.unit.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.demo_springboot_api.common.encoder.token.SHA256TokenEncoder;
import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import com.example.demo_springboot_api.modules.user.service.UserService;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder encoder;

  @InjectMocks private UserService userService;

  private String mockEncode(String input) {
    // Uses SHA256TokenEncoder because it's faster and should be enough for testing purpose
    TokenEncoder encoder = new SHA256TokenEncoder();
    return encoder.encode(input);
  }

  /*
  private Boolean mockMatches(String rawInput, String encodedString) {
    // Uses SHA256TokenEncoder because it's faster and should be secure for testing purpose
    TokenEncoder encoder = new SHA256TokenEncoder();
    return encoder.matches(rawInput, encodedString);
  }
  */

  private User getMockUser() {
    User user = new User();
    user.setId(Long.valueOf(100));
    user.setCode("big.j.awesome");
    user.setName("John");
    user.setEmail("john@gmail.com");
    user.setPassword("Klmnop123");
    user.setCreatedAt(new Date(1000000));
    user.setUpdatedAt(new Date(1000000));
    return user;
  }

  private void assertUserEquals(User u1, User u2) {
    assertEquals(u1.getId(), u2.getId());
    assertEquals(u1.getCode(), u2.getCode());
    assertEquals(u1.getName(), u2.getName());
    assertEquals(u1.getEmail(), u2.getEmail());
    assertEquals(u1.getPassword(), u2.getPassword());
    assertEquals(u1.getCreatedAt(), u2.getCreatedAt());
    assertEquals(u1.getUpdatedAt(), u2.getUpdatedAt());
  }

  @Test
  void addUser_shouldReturnUser_whenSuccessful() {
    User user = getMockUser();

    String encodedPassword = mockEncode(user.getPassword());

    when(userRepository.save(user)).thenReturn(user);
    when(encoder.encode(user.getPassword())).thenReturn(encodedPassword);

    User result = userService.addUser(user);

    user.setPassword(encodedPassword);
    assertUserEquals(user, result);
  }

  @Test
  void findById_shouldReturnUser_whenUserExists() {
    User user = getMockUser();

    when(userRepository.findById(Long.valueOf(user.getId()))).thenReturn(Optional.of(user));

    User result = userService.findById(Long.valueOf(user.getId()));

    assertUserEquals(user, result);
  }

  @Test
  void shouldThrowException_whenUserNotFound() {
    User user = getMockUser();

    when(userRepository.findById(Long.valueOf(user.getId()))).thenReturn(Optional.empty());

    assertThrows(
        UserNotFoundException.class, () -> userService.findById(Long.valueOf(user.getId())));
  }
}
