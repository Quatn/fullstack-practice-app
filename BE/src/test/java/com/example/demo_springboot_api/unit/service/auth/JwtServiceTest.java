package com.example.demo_springboot_api.unit.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo_springboot_api.modules.auth.config.ConfiguredUserDetails;
import com.example.demo_springboot_api.modules.auth.service.JwtService;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.unit.BaseUnitTest;
import com.example.demo_springboot_api.utils.MockData;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest extends BaseUnitTest {

  @InjectMocks private JwtService jwtService;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(
        jwtService, "SECRET", "AAAAAATHERESARATINTHEBUILDINGWITHUSITSGOTAGUNAAAAAAAAAA");

    ReflectionTestUtils.setField(jwtService, "TOKEN_EXPIRATION_SECONDS", 1800);
  }

  @Test
  void generateAndExtractToken() {
    User user = MockData.mockUser();

    ConfiguredUserDetails userDetails = new ConfiguredUserDetails(user);

    String token = jwtService.generateToken(userDetails);

    assertNotNull(token);

    String subject = jwtService.extractClaim(token, Claims::getSubject);

    assertEquals(subject, userDetails.getUsername());
  }
}
