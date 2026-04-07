package com.example.demo_springboot_api.unit.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import com.example.demo_springboot_api.common.errors.InvalidAuthSessionException;
import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import com.example.demo_springboot_api.modules.auth.repository.AuthSessionRepository;
import com.example.demo_springboot_api.modules.auth.service.AuthSessionService;
import com.example.demo_springboot_api.utils.MockData;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthSessionServiceTest {

  @Mock private AuthSessionRepository authSessionRepository;

  @InjectMocks private AuthSessionService authSessionService;

  @Test
  void addSession_shouldReturnSession_whenSuccessful() {
    AuthSession session = MockData.mockAuthSession();
    session.setId(null);

    doAnswer(
            invocation -> {
              // Make sure that the stub is correctly called by checking the uuid passed to it
              // Might check all of the arguments but I don't think it's necessary
              if (invocation.getArgument(0, AuthSession.class).getUUID() == session.getUUID()) {
                // Repo function will modify ref's id by new id
                session.setId(Long.valueOf(100));
                return session;
              }
              return null;
            })
        .when(authSessionRepository)
        .save(any());

    AuthSession result =
        authSessionService.addSession(
            session.getUser(),
            session.getTokenHash(),
            session.getUUID(),
            session.getCreatedAt(),
            session.getExpiresAt(),
            session.getRevoked(),
            session.getDeviceInfo());

    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(session.getId(), result.getId());
  }

  @Test
  void findByUUID_shouldReturnSession_whenSessionExists() {
    AuthSession session = MockData.mockAuthSession();

    when(authSessionRepository.findByUuid(session.getUUID())).thenReturn(Optional.of(session));

    AuthSession result = authSessionService.findByUUID(session.getUUID());

    assertNotNull(result);
    assertNotNull(result.getUUID());
    assertEquals(session.getUUID(), result.getUUID());
  }

  @Test
  void findByUUID_shouldThrowException_whenSessionNotFound() {
    AuthSession session = MockData.mockAuthSession();
    session.setUUID("IMAGINARY_UUID");

    when(authSessionRepository.findByUuid(session.getUUID())).thenReturn(Optional.empty());

    assertThrows(
        InvalidAuthSessionException.class, () -> authSessionService.findByUUID(session.getUUID()));
  }
}
