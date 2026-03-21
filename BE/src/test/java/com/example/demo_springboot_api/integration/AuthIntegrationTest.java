package com.example.demo_springboot_api.integration;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class AuthIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void register_shouldCreateUserAndReturnResponseEntityRegisterResponse_whenSuccessful()
      throws Exception {
    String json =
        """
            {
              "code": "john.j.aw",
              "name": "John",
              "email": "john@gmail.com",
              "password": "Klmnop123@hgh"
            }
        """;

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isCreated());
  }
}
