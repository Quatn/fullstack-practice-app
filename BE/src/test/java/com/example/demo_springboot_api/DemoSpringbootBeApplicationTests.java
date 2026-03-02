package com.example.demo_springboot_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoSpringbootBeApplicationTests {

	@Test
	void contextLoads() {
	}

}
