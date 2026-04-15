package com.example.demo_springboot_api.integration;

import com.example.demo_springboot_api.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
public abstract class BaseIntegrationTest extends BaseTest {
  @Autowired protected MockMvc mockMvc;
}
