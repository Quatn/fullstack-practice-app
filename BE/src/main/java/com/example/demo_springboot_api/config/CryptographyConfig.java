package com.example.demo_springboot_api.config;

import com.example.demo_springboot_api.common.encoder.token.SHA256TokenEncoder;
import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CryptographyConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenEncoder tokenEncoder() {
    return new SHA256TokenEncoder();
  }
}
