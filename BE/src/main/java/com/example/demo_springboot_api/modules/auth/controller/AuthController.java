package com.example.demo_springboot_api.modules.auth.controller;

import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.auth.dto.LoginResponse;
import com.example.demo_springboot_api.modules.auth.service.AuthService;
import com.example.demo_springboot_api.modules.auth.service.AuthSessionService;
import com.example.demo_springboot_api.modules.auth.service.ConfiguredUserDetails;
import com.example.demo_springboot_api.modules.auth.service.JwtService;
import com.example.demo_springboot_api.modules.user.entity.User;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(ModuleConstants.BASE_PATH)
public class AuthController {
  @Value("${REFRESH_TOKEN_SECRET}")
  private String REFRESH_TOKEN_SECRET;

  @Value("${REFRESH_TOKEN_EXPIRATION_SECONDS}")
  private long REFRESH_TOKEN_EXPIRATION_SECONDS;

  @Autowired private PasswordEncoder encoder;

  @Autowired private JwtService jwtService;

  @Autowired private AuthService authService;

  @Autowired private AuthSessionService authSessionService;

  @PostMapping(path = "/login")
  public @ResponseBody ResponseEntity<LoginResponse> login(
      @RequestParam String loginKey, @RequestParam String password) {

    User user = authService.login(loginKey, password);
    if (encoder.matches(password, user.getPassword())) {
      long now = System.currentTimeMillis();
      Date createdDate = new Date(now);
      Date expirationDate = new Date(now + 1000 * REFRESH_TOKEN_EXPIRATION_SECONDS);

      @SuppressWarnings("unchecked")
      String refreshToken =
          jwtService.generateToken(
              "refresh_token",
              new Pair[] {Pair.of("secret", REFRESH_TOKEN_SECRET)},
              createdDate,
              expirationDate);

      authSessionService.addSession(
          user, encoder.encode(refreshToken), createdDate, expirationDate, false, "" /* TODO */);

      ResponseCookie cookie =
          ResponseCookie.from(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken)
              .httpOnly(true)
              .secure(true)
              .maxAge(REFRESH_TOKEN_EXPIRATION_SECONDS)
              .sameSite("Strict")
              .build();

      return ResponseEntity.status(HttpStatus.OK)
          .header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(
              LoginResponse.success(
                  "Login successfully", jwtService.generateToken(new ConfiguredUserDetails(user))));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(LoginResponse.error("Login unsuccessfully", "Wrong email or password"));
  }

  /*
  @PostMapping(path = "/getall")
  public @ResponseBody ResponseEntity<BaseResponse<String, String>> getAllUsers() {
    String out = "";
    Iterator<User> users = userRepository.findAll().iterator();
    while (users.hasNext()) {
      User user = users.next();
      out += user.getName();
      if (users.hasNext()) {
        out += ", ";
      }
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(BaseResponse.success("Get all successfully", out));
  }
  */
}
