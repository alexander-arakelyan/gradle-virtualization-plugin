package org.bambrikii.gradle.virtualization.plugin.utils;

import org.gradle.api.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class LogUtils {
  private LogUtils() {
  }

  public static void logCommand(Logger logger, List<String> params) {
    logger.lifecycle("Running: " + params.stream().collect(Collectors.joining(" ")));
  }
}
