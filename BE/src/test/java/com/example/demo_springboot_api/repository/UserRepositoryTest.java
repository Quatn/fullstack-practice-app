package com.example.demo_springboot_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import com.example.demo_springboot_api.utils.MockData;
import com.example.demo_springboot_api.utils.TestDataFactory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserRepositoryTest extends BaseRepositoryTest {
  @Autowired private UserRepository userRepository;

  @Autowired private TestDataFactory testDataFactory;

  @Test
  void save_shouldSaveUser() {
    User user = MockData.mockUser();

    // Get the repo to add a new record
    user.setId(null);

    User saved = userRepository.save(user);

    assertEquals(user, saved);
    assertNotNull(saved.getId());
  }

  @Test
  void checkCodeAvailable_shouldReturnTrue_whenUserWithCodeDoesNotExists() {
    Boolean result = userRepository.checkCodeAvailable("user.with.this.code.should.not.exists");
    assertTrue(result);
  }

  @Test
  void checkCodeAvailable_shouldReturnFalse_whenUserWithCodeExists() {
    User user = testDataFactory.createAndSaveUser("unique.code.1");
    Boolean result = userRepository.checkCodeAvailable(user.getCode());
    assertFalse(result);
  }
}
