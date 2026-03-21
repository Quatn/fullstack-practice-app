package com.example.demo_springboot_api.integration;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class UserIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldCreateUser() throws Exception {
    String json =
        """
            {
              "username": "john",
              "password": "123456"
            }
        """;

    mockMvc
        .perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated());
  }
}
