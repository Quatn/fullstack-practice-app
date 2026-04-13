package com.example.demo_springboot_api.unit;

import com.example.demo_springboot_api.BaseTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest extends BaseTest {
  @Mock protected PasswordEncoder encoder;
}
