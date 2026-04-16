package com.example.demo_springboot_api.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.generated.ErrorCode;
import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.utils.MockData;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Testcontainers
@Transactional
class JwtAuthIntegrationTest extends BaseIntegrationTest {
  /*
   * HAPPY CASES
   */

  @Test
  @DisplayName("Should return a new access token from the refresh token in the httpOnly cookie")
  void shouldRefreshToken() throws Exception {
    // === PREPARE REFRESH TOKEN ===
    User mockUser = MockData.mockUser();

    String registerForm =
        """
            {
              "code": "%s",
              "name": "%s",
              "email": "%s",
              "password": "%s"
            }
        """
            .formatted(
                mockUser.getCode(),
                mockUser.getName(),
                mockUser.getEmail(),
                mockUser.getPassword());

    System.out.println(registerForm);

    MvcResult result =
        mockMvc
            .perform(
                post(ModuleConstants.BASE_PATH + "/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(registerForm))
            .andExpect(status().isCreated())
            .andExpect(cookie().exists(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
            .andExpect(cookie().httpOnly(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
            .andExpect(cookie().secure(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());

    String refreshToken =
        result.getResponse().getCookie(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME).getValue();

    System.out.println(refreshToken);

    assertNotNull(refreshToken);

    // === USE TOKEN TO GET ACCESS TOKEN ===
    mockMvc
        .perform(
            get(ModuleConstants.BASE_PATH + "/token/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken)))
        .andExpect(status().isOk())
        .andDo(
            (r) -> {
              System.out.println(r.getResponse().getContentAsString());
            });
  }

  /*
   * ERROR CASES
   */

  @Test
  @DisplayName(
      "Should not return a new access token if the refresh token is missing (user is not"
          + " authenticated)")
  void shouldNotRefreshToken_whenRefreshTokenIsMissing() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                get(ModuleConstants.BASE_PATH + "/token/refresh")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());
    ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    ApiResponse<Object> response =
        objectMapper.readValue(result.getResponse().getContentAsString(), ApiResponse.class);

    assertEquals(ErrorCode.AUTH_STATE_ERR_AUTHENTICATED_ONLY, response.error);
  }

  @Test
  @DisplayName("Should not return a new access token if the refresh token is malformed")
  void shouldNotRefreshToken_whenRefreshTokenIsMalformed() throws Exception {
    String malformedToken = "malformedToken";

    MvcResult result =
        mockMvc
            .perform(
                get(ModuleConstants.BASE_PATH + "/token/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(new Cookie(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, malformedToken)))
            .andExpect(status().isBadRequest())
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());
    ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    ApiResponse<Object> response =
        objectMapper.readValue(result.getResponse().getContentAsString(), ApiResponse.class);

    assertEquals(ErrorCode.AUTH_TOKEN_ERR_MALFORMED_TOKEN, response.error);
  }

  // TODO: Test revoked token case & revoked auth session
}
