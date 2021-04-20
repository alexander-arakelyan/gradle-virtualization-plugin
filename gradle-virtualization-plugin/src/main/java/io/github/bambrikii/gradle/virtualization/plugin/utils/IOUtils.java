package io.github.bambrikii.gradle.virtualization.plugin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class IOUtils {
  public static String toString(InputStreamReader inputStreamReader) {
    StringBuilder textBuilder = new StringBuilder();
    try (Reader reader = new BufferedReader(inputStreamReader)) {
      int c;
      while ((c = reader.read()) != -1) {
        textBuilder.append((char) c);
      }
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read stream", ex);
    }
    return textBuilder.toString();
  }
}
