package com.example.demo_springboot_api;

import org.springframework.boot.SpringApplication;

public class TestDemoSpringbootBeApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoSpringbootBeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
