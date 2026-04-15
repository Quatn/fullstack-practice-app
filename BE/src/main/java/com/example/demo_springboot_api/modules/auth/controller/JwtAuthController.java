package com.example.demo_springboot_api.modules.auth.controller;

import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import com.example.demo_springboot_api.common.errors.AuthenticatedOnlyException;
import com.example.demo_springboot_api.common.errors.ExpiredAuthSessionException;
import com.example.demo_springboot_api.common.errors.InvalidAuthSessionException;
import com.example.demo_springboot_api.common.errors.RevokedAuthSessionException;
import com.example.demo_springboot_api.common.utils.ResponseHelper;
import com.example.demo_springboot_api.modules.auth.config.ConfiguredUserDetails;
import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.auth.dto.TokenRefreshResponse;
import com.example.demo_springboot_api.modules.auth.dto.TokenRefreshResponseData;
import com.example.demo_springboot_api.modules.auth.dto.UserState;
import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import com.example.demo_springboot_api.modules.auth.service.AuthSessionService;
import com.example.demo_springboot_api.modules.auth.service.JwtService;
import com.example.demo_springboot_api.modules.user.entity.User;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(ModuleConstants.BASE_PATH + "/token")
public class JwtAuthController {
  @Autowired private AuthSessionService authSessionService;
  @Autowired private TokenEncoder tokenEncoder;
  @Autowired private JwtService jwtService;

  @GetMapping(path = "/refresh")
  public @ResponseBody ResponseEntity<TokenRefreshResponse> tokenRefresh(
      @CookieValue(value = ModuleConstants.REFRESH_TOKEN_COOKIE_NAME, required = false)
          String refreshToken) {

    if (refreshToken == null) {
      throw new AuthenticatedOnlyException("Authentication required");
    }

    String tokenUUID = jwtService.extractUsername(refreshToken);
    AuthSession session = authSessionService.findByUUID(tokenUUID);

    String storedHash = session.getTokenHash();
    if (!tokenEncoder.matches(refreshToken, storedHash)) {
      throw new InvalidAuthSessionException("Auth session hash does not match server-stored hash");
    }

    long now = System.currentTimeMillis();
    Date currentDate = new Date(now);

    if (session.getRevoked()) {
      throw new RevokedAuthSessionException("Auth session revoked");
    }

    if (currentDate.after(session.getExpiresAt())) {
      throw new ExpiredAuthSessionException("Auth session expired");
    }

    // TODO: Also match devide info, or send warning over unmatched device info

    // After this point the refreshToken and auth session is considered valid, the server shall now
    // grant an access token to the client

    User user = session.getUser();
    String accessToken = jwtService.generateToken(new ConfiguredUserDetails(user));
    UserState userState = new UserState(user);

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseHelper.success(
                "Authenticated",
                new TokenRefreshResponseData(userState, accessToken),
                TokenRefreshResponse::new));
  }
}
