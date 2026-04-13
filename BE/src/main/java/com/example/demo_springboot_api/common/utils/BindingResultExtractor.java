package com.example.demo_springboot_api.common.utils;

import java.util.List;
import org.springframework.validation.BindingResult;

public class BindingResultExtractor {
  /**
   * Gets all of the field errors from a BindingResult and return it as a String message in the
   * format: "{field}: {rejectedValue} (message), {field2}: {rejectedValue2} (message2), ..."
   *
   * @param BindingResult
   * @return String
   */
  public static String getFieldRejectedValueMessage(BindingResult bindingResult) {
    List<String> errors =
        bindingResult.getFieldErrors().stream()
            .map(
                (err) ->
                    "%s: %s (%s)"
                        .formatted(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
            .toList();

    return errors.stream().reduce((acc, s) -> acc + ", " + s).orElse("No message");
  }
}
