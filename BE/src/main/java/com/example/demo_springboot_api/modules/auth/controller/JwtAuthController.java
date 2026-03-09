package com.example.demo_springboot_api.modules.auth.controller;

import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import com.example.demo_springboot_api.modules.auth.service.ConfiguredUserDetailsService;
import com.example.demo_springboot_api.modules.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(ModuleConstants.BASE_PATH + "/token")
public class JwtAuthController {
  private final ConfiguredUserDetailsService userDetailsService;
  private final JwtService jwtService;

  @Autowired
  public JwtAuthController(ConfiguredUserDetailsService userDetailsService, JwtService jwtService) {
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }

  @GetMapping(path = "/refresh")
  public @ResponseBody String refreshToken(
      @CookieValue(ModuleConstants.REFRESH_TOKEN_COOKIE_NAME) String refreshToken) {

    String username = jwtService.extractUsername(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (jwtService.validateToken(refreshToken, userDetails)) {}
    ;

    /*
      User n = new User();
      n.setCode(code);
      n.setName(name);
      n.setEmail(email);
      userRepository.save(n);
    */
    return "Saved";
  }
}
