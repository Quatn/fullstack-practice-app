package com.example.demo_springboot_api.common.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.stereotype.Component;

@Component
public class HashingService {

  private static final String ALGORITHM = "SHA-256";

  public String hash(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance(ALGORITHM);
      byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  public boolean matches(String rawInput, String storedHash) {
    String computed = hash(rawInput);

    return MessageDigest.isEqual(
        computed.getBytes(StandardCharsets.UTF_8), storedHash.getBytes(StandardCharsets.UTF_8));
  }
}
