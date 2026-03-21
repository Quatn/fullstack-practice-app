package com.example.demo_springboot_api.integration;

import com.example.demo_springboot_api.TestcontainersConfiguration;
import com.example.demo_springboot_api.config.SecurityConfig;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;

@Import({TestcontainersConfiguration.class, SecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public abstract class BaseIntegrationTest {}
