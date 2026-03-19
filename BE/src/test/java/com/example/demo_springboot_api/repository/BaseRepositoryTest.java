package com.example.demo_springboot_api.repository;

import com.example.demo_springboot_api.TestcontainersConfiguration;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
public abstract class BaseRepositoryTest {}
