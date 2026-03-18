package com.example.demo_springboot_api.common.encoder.token;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class SHA256TokenEncoder implements TokenEncoder {
  private static final String ALGORITHM = "SHA-256";

  public String encode(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance(ALGORITHM);
      byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  public boolean matches(String rawInput, String storedHash) {
    String computed = encode(rawInput);

    return MessageDigest.isEqual(
        computed.getBytes(StandardCharsets.UTF_8), storedHash.getBytes(StandardCharsets.UTF_8));
  }
}
