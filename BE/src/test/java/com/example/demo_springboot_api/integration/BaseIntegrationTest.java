package com.example.demo_springboot_api.integration;

import com.example.demo_springboot_api.BaseTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@AutoConfigureMockMvc(addFilters = false)
public abstract class BaseIntegrationTest extends BaseTest {}
