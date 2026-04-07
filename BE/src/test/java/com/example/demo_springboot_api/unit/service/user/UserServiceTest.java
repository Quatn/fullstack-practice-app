package com.example.demo_springboot_api.unit.service.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.demo_springboot_api.common.encoder.token.SHA256TokenEncoder;
import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import com.example.demo_springboot_api.modules.user.service.UserService;
import com.example.demo_springboot_api.utils.CustomAsserts;
import com.example.demo_springboot_api.utils.MockData;
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

  @Test
  void addUser_shouldReturnUser_whenSuccessful() {
    User user = MockData.mockUser();

    String encodedPassword = mockEncode(user.getPassword());

    when(userRepository.save(user)).thenReturn(user);
    when(encoder.encode(user.getPassword())).thenReturn(encodedPassword);

    User result = userService.addUser(user);

    user.setPassword(encodedPassword);
    CustomAsserts.assertUserEquals(user, result);
  }

  @Test
  void findById_shouldReturnUser_whenUserExists() {
    User user = MockData.mockUser();

    when(userRepository.findById(Long.valueOf(user.getId()))).thenReturn(Optional.of(user));

    User result = userService.findById(Long.valueOf(user.getId()));

    CustomAsserts.assertUserEquals(user, result);
  }

  @Test
  void findById_shouldThrowException_whenUserNotFound() {
    User user = MockData.mockUser();
    user.setId(Long.valueOf(-1));

    when(userRepository.findById(Long.valueOf(user.getId()))).thenReturn(Optional.empty());

    assertThrows(
        UserNotFoundException.class, () -> userService.findById(Long.valueOf(user.getId())));
  }
}
