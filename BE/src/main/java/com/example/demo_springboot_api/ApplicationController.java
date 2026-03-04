package com.example.demo_springboot_api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {
  // Returns a welcome message on the root path of the api. This is also used for
  // container health check.
  @GetMapping(path = "/")
  public @ResponseBody String welcome() {
    return "Welcome to demo springboot app's api";
  }
}
