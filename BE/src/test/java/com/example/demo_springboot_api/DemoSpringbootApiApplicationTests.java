package com.example.demo_springboot_api;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoSpringbootApiApplicationTests {
  @Autowired private ApplicationController applicationController;

  @Autowired DataSource dataSource;

  @Test
  void contextLoads() throws Exception {
    System.out.println(dataSource);
    assertThat(applicationController).isNotNull();
  }
}
