package com.example.demo_springboot_api.unit.encoder.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo_springboot_api.common.encoder.token.SHA256TokenEncoder;
import com.example.demo_springboot_api.common.encoder.token.TokenEncoder;
import org.junit.jupiter.api.Test;

class SHA256TokenEncoderTest {

  @Test
  void encode_shouldReturnSameResult_whenEncodeSameString() {
    TokenEncoder encoder = new SHA256TokenEncoder();

    String result1 = encoder.encode("HELLO WORLD");
    String result2 = encoder.encode("HELLO WORLD");

    assertEquals(result1, result2);
  }

  @Test
  void encode_shouldReturnDifferentResult_whenEncodeDifferentString() {
    TokenEncoder encoder = new SHA256TokenEncoder();

    String result1 = encoder.encode("HELLO WORLD");
    String result2 = encoder.encode("HELLO WORLD ASCBASKJN");

    assertNotEquals(result1, result2);
  }

  @Test
  void matches_shouldReturnTrue_whenCorrectStringIsMatched() {
    String input = "FOO BAR BAZ";
    TokenEncoder encoder = new SHA256TokenEncoder();

    String result = encoder.encode(input);

    assertTrue(encoder.matches(input, result));
  }

  @Test
  void matches_shouldReturnFalse_whenIncorrectStringIsMatched() {
    String input = "FOO BAR BAZ";
    String wrongInput = "FOO BAR HELLO";
    TokenEncoder encoder = new SHA256TokenEncoder();

    String result = encoder.encode(input);

    assertFalse(encoder.matches(wrongInput, result));
  }
}
