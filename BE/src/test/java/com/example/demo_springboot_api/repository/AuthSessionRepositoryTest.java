package com.example.demo_springboot_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import com.example.demo_springboot_api.modules.auth.repository.AuthSessionRepository;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.utils.TestDataFactory;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class AuthSessionRepositoryTest extends BaseRepositoryTest {
  @Autowired private AuthSessionRepository authSessionRepository;

  @Autowired private TestDataFactory testDataFactory;

  @Test
  void save_shouldSaveAuthSession() {
    User user = testDataFactory.createAndSaveUser("unique.code.1");
    long now = System.currentTimeMillis();
    Date createdDate = new Date(now);
    Date expirationDate = new Date(now + 1000 * 90);

    AuthSession session = new AuthSession();
    session.setUser(user);
    session.setTokenHash("test_token_0");
    session.setUUID("test_uuid_0");
    session.setCreatedAt(createdDate);
    session.setExpiresAt(expirationDate);
    session.setRevoked(false);

    AuthSession saved = authSessionRepository.save(session);

    // saved() should return the reference to the same object
    assertEquals(session, saved);

    // If saved successfully then the id will no longer be null
    assertNotNull(saved.getId());
  }

  @Test
  void findByUserId_shouldReturnAuthSession_whenExists() {
    User user = testDataFactory.createAndSaveUser("unique.code.1");
    String testToken = "test_token_1";
    String testUUID = "test_uuid_1";
    AuthSession session = testDataFactory.createAndSaveSession(user, testToken, testUUID);

    Optional<AuthSession> result = authSessionRepository.findByUserId(user.getId());
    assertNotNull(result.orElse(null));
    assertEquals(session.getId(), result.get().getId());
    assertEquals(session.getUUID(), result.get().getUUID());
  }

  @Test
  void findByUserId_shouldReturnEmpty_whenNotExists() {
    Optional<AuthSession> result = authSessionRepository.findByUserId(Long.valueOf(-1));
    assertTrue(result.isEmpty());
  }

  @Test
  void findByUuid_shouldReturnAuthSession_whenExists() {
    User user = testDataFactory.createAndSaveUser("unique.code.2");
    String testToken = "test_token_2";
    String testUUID = "test_uuid_2";
    AuthSession session = testDataFactory.createAndSaveSession(user, testToken, testUUID);

    Optional<AuthSession> result = authSessionRepository.findByUuid(testUUID);
    assertNotNull(result.orElse(null));
    assertEquals(session.getId(), result.get().getId());
    assertEquals(session.getUUID(), result.get().getUUID());
  }

  @Test
  void findByUuid_shouldReturnEmpty_whenNotExists() {
    Optional<AuthSession> result = authSessionRepository.findByUuid("IMPOSSIBLE UUID");
    assertTrue(result.isEmpty());
  }
}
