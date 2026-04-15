package com.example.demo_springboot_api;

import com.example.demo_springboot_api.config.SecurityConfig;
import org.springframework.context.annotation.Import;

@Import({TestcontainersConfiguration.class, SecurityConfig.class})
public abstract class BaseTest {}
