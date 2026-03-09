package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import com.example.demo_springboot_api.modules.auth.repository.AuthSessionRepository;
import com.example.demo_springboot_api.modules.user.entity.User;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthSessionService {
  @Autowired private AuthSessionRepository authSessionRepository;

  public AuthSession addSession(
      User user,
      String tokenHash,
      Date createdAt,
      Date expiresAt,
      Boolean revoked,
      String deviceInfo) {
    AuthSession session = new AuthSession();
    session.setUser(user);
    session.setTokenHash(tokenHash);
    session.setCreatedAt(createdAt);
    session.setExpiresAt(expiresAt);
    session.setRevoked(revoked);
    session.setDeviceInfo(deviceInfo);

    return authSessionRepository.save(session);
  }
}
