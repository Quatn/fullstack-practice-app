package com.example.demo_springboot_api.unit.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.common.errors.WrongCredentialException;
import com.example.demo_springboot_api.modules.auth.service.AuthService;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import com.example.demo_springboot_api.unit.BaseUnitTest;
import com.example.demo_springboot_api.utils.MockData;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest extends BaseUnitTest {
  @Mock private UserRepository userRepository;

  @InjectMocks private AuthService authService;

  @Test
  void login_shouldReturnUser_whenSuccessful() {
    User user = MockData.mockUser();
    String correctPassword = "UnhashedButCorrectPassword";

    when(userRepository.findByCode(user.getCode())).thenReturn(Optional.of(user));
    when(encoder.matches(correctPassword, user.getPassword())).thenReturn(true);

    User result = authService.login(user.getCode(), correctPassword);

    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(user.getId(), result.getId());
  }

  @Test
  void login_shouldThrowError_whenUserNotFound() {
    User user = MockData.mockUser();

    when(userRepository.findByCode(user.getCode())).thenReturn(Optional.empty());

    assertThrows(
        UserNotFoundException.class,
        () -> {
          authService.login(user.getCode(), user.getPassword());
        });
  }

  @Test
  void login_shouldThrowError_whenWrongCredential() {
    User user = MockData.mockUser();
    String wrongPassword =
        "APasswordThatIsUnhashedAndConformsToDataFormatRequirementButIsNotCorrect";

    when(userRepository.findByCode(user.getCode())).thenReturn(Optional.of(user));
    when(encoder.matches(wrongPassword, user.getPassword())).thenReturn(false);

    assertThrows(
        WrongCredentialException.class,
        () -> {
          authService.login(user.getCode(), wrongPassword);
        });
  }

  @Test
  void checkCodeAvailable_shouldReturnTrue_whenCodeIsAvailable() {
    User user = MockData.mockUser();

    when(userRepository.checkCodeAvailable(user.getCode())).thenReturn(true);

    Boolean result = authService.checkCodeAvailable(user.getCode());

    assertTrue(result);
  }

  @Test
  void checkCodeAvailable_shouldReturnFalse_whenCodeIsNotAvailable() {
    User user = MockData.mockUser();

    when(userRepository.checkCodeAvailable(user.getCode())).thenReturn(false);

    Boolean result = authService.checkCodeAvailable(user.getCode());

    assertFalse(result);
  }
}
