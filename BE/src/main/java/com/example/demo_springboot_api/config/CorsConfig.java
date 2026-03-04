package com.example.demo_springboot_api.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
  @Value("${WEB_URL}")
  private String WEB_URL;

  @Bean
  UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowedOrigins(Arrays.asList(WEB_URL));
    configuration.setAllowedMethods(Arrays.asList("GET","PUT","POST","DELETE","HEAD","OPTIONS"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
