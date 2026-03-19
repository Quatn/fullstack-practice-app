package com.example.demo_springboot_api.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.auth.controller.AuthController;
import com.example.demo_springboot_api.modules.auth.dto.LoginRequest;
import com.example.demo_springboot_api.modules.auth.dto.LoginResponse;
import com.example.demo_springboot_api.modules.auth.dto.UserState;
import com.example.demo_springboot_api.modules.auth.service.AuthService;
import com.example.demo_springboot_api.modules.auth.service.AuthSessionService;
import com.example.demo_springboot_api.modules.auth.service.JwtService;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.service.UserService;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends BaseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private JwtService jwtService;

  @MockitoBean private AuthService authService;

  @MockitoBean private UserService userService;

  @MockitoBean private AuthSessionService authSessionService;

  @MockitoBean private TokenEncoder tokenEncoder;

  private User getMockUser() {
    User user = new User();
    user.setId(Long.valueOf(100));
    user.setCode("big.j.awesome");
    user.setName("John");
    user.setEmail("john@gmail.com");
    user.setPassword("Klmnop123");
    user.setCreatedAt(new Date(1000000));
    user.setUpdatedAt(new Date(1000000));
    return user;
  }

  private void assertUserEquals(User u1, User u2) {
    assertEquals(u1.getId(), u2.getId());
    assertEquals(u1.getCode(), u2.getCode());
    assertEquals(u1.getName(), u2.getName());
    assertEquals(u1.getEmail(), u2.getEmail());
    assertEquals(u1.getPassword(), u2.getPassword());
    assertEquals(u1.getCreatedAt(), u2.getCreatedAt());
    assertEquals(u1.getUpdatedAt(), u2.getUpdatedAt());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  void login_shouldReturnResponseEntityLoginResponse() throws Exception {
    User user = getMockUser();
    String loginKey = user.getCode();
    String password = user.getPassword();

    LoginRequest loginForm = new LoginRequest(loginKey, password);

    // Encode password because the service will return the password that's saved on the database,
    // which is encoded.
    user.setPassword(mockEncode(password));
    when(authService.login(loginForm.loginKey(), loginForm.password())).thenReturn(user);
    when(encoder.matches(loginForm.password(), user.getPassword()))
        .thenReturn(mockMatches(loginForm.password(), user.getPassword()));
    when(jwtService.generateToken(anyString(), any(), any(Date.class), any(Date.class)))
        .thenReturn("mock-refresh-token");

    ObjectMapper objectMapper = new ObjectMapper();

    MvcResult result =
        mockMvc
            .perform(
                post(ModuleConstants.BASE_PATH + "/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginForm)))
            .andExpect(status().isOk())
            .andExpect(status().isOk())
            .andExpect(cookie().exists(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME))
            .andExpect(
                cookie().value(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, "mock-refresh-token"))
            .andExpect(cookie().httpOnly(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
            .andExpect(cookie().secure(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, true))
            .andReturn();

    String json = result.getResponse().getContentAsString();
    LoginResponse response = objectMapper.readValue(json, LoginResponse.class);

    UserState userState = response.data.userState();
    assertEquals(user.getId().toString(), userState.id());
    assertEquals(user.getName(), userState.name());
    assertEquals(user.getEmail(), userState.email());
    assertArrayEquals(user.getAccessPrivilegesArray(), userState.accessPrivileges());
  }
}
