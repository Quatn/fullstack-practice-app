package com.example.demo_springboot_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest extends BaseRepositoryTest {
  @Autowired private UserRepository userRepository;

  private User getMockUser() {
    User user = new User();
    user.setId(Long.valueOf(100));
    user.setCode("big.j.awesome");
    user.setName("John");
    user.setEmail("john@gmail.com");
    user.setPassword("Klmnop123_HASHED_PASSWORD");
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
  void save_shouldSaveUser() {
    User user = getMockUser();

    // Sets id to null so that userRepository inserts as a new row.
    user.setId(null);

    User saved = userRepository.save(user);

    // saved() should return the reference to the same user object
    assertEquals(user, saved);

    // If saved successfully then the id of the  will no longer be null
    assertNotNull(saved.getId());
  }
}
