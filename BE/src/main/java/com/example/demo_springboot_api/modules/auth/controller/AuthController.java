package com.example.demo_springboot_api.modules.auth.controller;

import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import com.example.demo_springboot_api.common.errors.InvalidDataException;
import com.example.demo_springboot_api.common.utils.ResponseHelper;
import com.example.demo_springboot_api.generated.ErrorCode;
import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.auth.dto.LoginForm;
import com.example.demo_springboot_api.modules.auth.dto.LoginResponse;
import com.example.demo_springboot_api.modules.auth.dto.LoginResponseData;
import com.example.demo_springboot_api.modules.auth.dto.RegisterForm;
import com.example.demo_springboot_api.modules.auth.dto.RegisterResponse;
import com.example.demo_springboot_api.modules.auth.dto.RegisterResponseData;
import com.example.demo_springboot_api.modules.auth.dto.UserState;
import com.example.demo_springboot_api.modules.auth.service.AuthService;
import com.example.demo_springboot_api.modules.auth.service.AuthSessionService;
import com.example.demo_springboot_api.modules.auth.service.JwtService;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.service.UserService;
import java.util.Date;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

record AuthResponseDataBundle(ResponseCookie cookie, UserState userState) {}

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

  @Autowired private UserService userService;

  @Autowired private AuthSessionService authSessionService;

  @Autowired private TokenEncoder tokenEncoder;

  @PostMapping(path = "/login")
  public @ResponseBody ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm) {
    User user = authService.login(loginForm.loginKey(), loginForm.password());
    if (encoder.matches(loginForm.password(), user.getPassword())) {
      AuthResponseDataBundle bundle = addAuthSessionAndCreateCookie(user);

      return ResponseEntity.status(HttpStatus.OK)
          .header(HttpHeaders.SET_COOKIE, bundle.cookie().toString())
          .body(
              ResponseHelper.success(
                  "Login successfully",
                  new LoginResponseData(bundle.userState()),
                  LoginResponse::new));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Login unsuccessfully: Wrong email or password",
                ErrorCode.AUTH_LOGIN_ERR_WRONG_CREDENTIAL,
                LoginResponse::new));
  }

  @PostMapping(path = "/register")
  public @ResponseBody ResponseEntity<RegisterResponse> register(
      @RequestBody RegisterForm registerForm) {

    if (!authService.checkCodeAvailable(registerForm.code())) {
      throw new InvalidDataException("Unable to register: user code already taken.");
    }

    if (!authService.checkEmailAvailable(registerForm.email())) {
      throw new InvalidDataException("Unable to register: email already taken.");
    }

    User n = new User();
    n.setCode(registerForm.code());
    n.setName(registerForm.name());
    n.setEmail(registerForm.email());
    n.setPassword(registerForm.password());
    User user = userService.addUser(n);

    AuthResponseDataBundle bundle = addAuthSessionAndCreateCookie(user);

    return ResponseEntity.status(HttpStatus.CREATED)
        .header(HttpHeaders.SET_COOKIE, bundle.cookie().toString())
        .body(
            ResponseHelper.success(
                "Register successfully",
                new RegisterResponseData(bundle.userState()),
                RegisterResponse::new));
  }

  private AuthResponseDataBundle addAuthSessionAndCreateCookie(User user) {
    long now = System.currentTimeMillis();
    Date createdDate = new Date(now);
    Date expirationDate = new Date(now + 1000 * REFRESH_TOKEN_EXPIRATION_SECONDS);

    String uuid = UUID.randomUUID().toString();

    @SuppressWarnings("unchecked")
    String refreshToken =
        jwtService.generateToken(
            uuid,
            new Pair[] {Pair.of("secret", REFRESH_TOKEN_SECRET)},
            createdDate,
            expirationDate);

    authSessionService.addSession(
        user,
        tokenEncoder.encode(refreshToken),
        uuid,
        createdDate,
        expirationDate,
        false,
        "" /* TODO: Get system info too */);

    UserState userState = new UserState(user);

    ResponseCookie cookie =
        ResponseCookie.from(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken)
            .httpOnly(true)
            .secure(true)
            .maxAge(REFRESH_TOKEN_EXPIRATION_SECONDS)
            .sameSite("None")
            .build();

    // String accessToken = jwtService.generateToken(new ConfiguredUserDetails(user));

    return new AuthResponseDataBundle(cookie, userState);
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
