package com.example.demo_springboot_api.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.utils.MockData;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Transactional
class AuthIntegrationTest extends BaseIntegrationTest {
  /*
   * HAPPY CASES
   */

  @Test
  @DisplayName("User can register and receives secure refresh token cookie")
  void shouldRegisterUser() throws Exception {
    User mockUser = MockData.mockUser();

    String json =
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

    System.out.println(json);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isCreated())
        .andExpect(cookie().exists(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
        .andExpect(cookie().httpOnly(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
        .andExpect(cookie().secure(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });
  }

  @Test
  @DisplayName("User can log in successfully and receives secure refresh token cookie")
  void shouldLoginSuccessfully() throws Exception {
    // === PREPARE USER ACCOUNT ===
    User mockUser = MockData.mockUser();

    String preExistingUser =
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

    System.out.println(preExistingUser);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preExistingUser))
        .andExpect(status().isCreated())
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });

    // === TEST LOGIN ===
    String json =
        """
            {
              "loginKey": "%s",
              "password": "%s"
            }
        """
            .formatted(mockUser.getCode(), mockUser.getPassword());

    System.out.println(json);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk())
        .andExpect(cookie().exists(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
        .andExpect(cookie().httpOnly(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
        .andExpect(cookie().secure(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });
  }

  /*
   * ERROR CASES
   */

  @Test
  @DisplayName(
      "If any field of the register form is invalid, returns a BAD REQUEST error and does not"
          + " register user (does not create a auth session and does not return a refresh token)")
  void shouldNotRegisterUser_whenFormFieldsAreNotValid() throws Exception {
    User mockUser = MockData.mockUser();

    String json =
        """
            {
              "code": "%s",
              "name": "%s",
              "email": "%s",
              "password": "%s"
            }
        """;

    String invalidCode = "invalid code";
    String invalidName = "";
    String invalidEmail = "invalid email";
    String invalidPassword = "invalid password";
    String[] invalidForms = {
      json.formatted(invalidCode, mockUser.getName(), mockUser.getEmail(), mockUser.getPassword()),
      json.formatted(mockUser.getCode(), invalidName, mockUser.getEmail(), mockUser.getPassword()),
      json.formatted(mockUser.getCode(), mockUser.getName(), invalidEmail, mockUser.getPassword()),
      json.formatted(mockUser.getCode(), mockUser.getName(), mockUser.getEmail(), invalidPassword),
      json.formatted(invalidCode, invalidName, invalidEmail, invalidPassword),
    };

    for (String invalidForm : invalidForms) {
      System.out.println(invalidForm);

      mockMvc
          .perform(
              post(ModuleConstants.BASE_PATH + "/register")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(invalidForm))
          .andExpect(status().isBadRequest())
          .andExpect(cookie().doesNotExist(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
          .andDo(
              (result) -> {
                System.out.println(result.getResponse().getContentAsString());
              });
    }
  }

  @Test
  @DisplayName(
      "If any field of the login form is invalid, returns a BAD REQUEST error and does not log"
          + " user in (does not create a auth session and does not return a refresh token)")
  void shouldNotLoginUser_whenFormFieldsAreNotValid() throws Exception {
    // === PREPARE USER ACCOUNT ===
    User mockUser = MockData.mockUser();

    String preExistingUser =
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

    System.out.println(preExistingUser);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preExistingUser))
        .andExpect(status().isCreated())
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });

    // === TEST LOGIN ===
    String invalidLoginKey = "invalid login key";
    String invalidPassword = "invalid password";
    String json =
        """
            {
              "loginKey": "%s",
              "password": "%s"
            }
        """;

    String[] invalidFroms = {
      json.formatted(invalidLoginKey, mockUser.getPassword()),
      json.formatted(mockUser.getCode(), invalidPassword),
      json.formatted(invalidLoginKey, invalidPassword),
    };

    for (String invalidForm : invalidFroms) {
      System.out.println(invalidForm);

      mockMvc
          .perform(
              post(ModuleConstants.BASE_PATH + "/login")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(invalidForm))
          .andExpect(status().isBadRequest())
          .andExpect(cookie().doesNotExist(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
          .andDo(
              (result) -> {
                System.out.println(result.getResponse().getContentAsString());
              });
    }
  }

  @Test
  @DisplayName(
      "If credentials (loginKey or password) are wrong, does not log the user in a throw either a"
          + " NOT FOUND or a UNAUTHORIZED response.")
  void shouldNotLoginUser_whenCredentialsAreWrong() throws Exception {
    // === PREPARE USER ACCOUNT ===
    User mockUser = MockData.mockUser();

    String preExistingUser =
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

    System.out.println(preExistingUser);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preExistingUser))
        .andExpect(status().isCreated())
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });

    // === TEST LOGIN ===
    String json =
        """
            {
              "loginKey": "%s",
              "password": "%s"
            }
        """;

    String wrongLoginKeyForm = json.formatted("valid.but.wrong.login.key", mockUser.getPassword());
    String wrongPasswordForm = json.formatted(mockUser.getCode(), "ValidButWrongPassword@123");

    System.out.println(wrongLoginKeyForm);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(wrongLoginKeyForm))
        .andExpect(status().isNotFound())
        .andExpect(cookie().doesNotExist(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });

    System.out.println(wrongPasswordForm);

    mockMvc
        .perform(
            post(ModuleConstants.BASE_PATH + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(wrongPasswordForm))
        .andExpect(status().isUnauthorized())
        .andExpect(cookie().doesNotExist(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
        .andDo(
            (result) -> {
              System.out.println(result.getResponse().getContentAsString());
            });
  }
}
