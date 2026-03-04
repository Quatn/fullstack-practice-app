package com.example.demo_springboot_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/")
                    .permitAll()
                    .requestMatchers("/test")
                    .permitAll()
                    .requestMatchers("/test2")
                    .permitAll()
                    .requestMatchers("/user/dev/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    // .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }
}
