package com.example.demo_springboot_api.controller;

import com.example.demo_springboot_api.BaseTest;
import com.example.demo_springboot_api.common.encoder.token.SHA256TokenEncoder;
import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class BaseControllerTest extends BaseTest {
  @MockitoBean protected UserDetailsService userDetailsService;

  @MockitoBean protected PasswordEncoder encoder;

  protected String mockEncode(String input) {
    // Uses SHA256TokenEncoder because it's faster and should be enough for testing purpose
    TokenEncoder encoder = new SHA256TokenEncoder();
    return encoder.encode(input);
  }

  protected Boolean mockMatches(String rawInput, String encodedString) {
    // Uses SHA256TokenEncoder because it's faster and should be secure for testing purpose
    TokenEncoder encoder = new SHA256TokenEncoder();
    return encoder.matches(rawInput, encodedString);
  }
}
