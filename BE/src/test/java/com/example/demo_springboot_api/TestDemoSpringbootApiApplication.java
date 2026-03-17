package com.example.demo_springboot_api;

import org.springframework.boot.SpringApplication;

public class TestDemoSpringbootApiApplication {

  public static void main(String[] args) {
    SpringApplication.from(DemoSpringbootApiApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
