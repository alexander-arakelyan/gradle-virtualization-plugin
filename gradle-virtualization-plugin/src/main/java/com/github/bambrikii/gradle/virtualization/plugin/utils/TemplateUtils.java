package com.github.bambrikii.gradle.virtualization.plugin.utils;

import org.gradle.api.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Function;

public class TemplateUtils {
    private TemplateUtils() {
    }

    public static void prepareDirs(Path path, Logger logger) {
        File dir = path.toFile();
        if (dir.exists()) {
            return;
        }
        logger.lifecycle("Creating directory " + path);
        dir.mkdirs();
    }

    public static void prepareFile(
            String targetFileName,
            InputStream inputStream,
            Logger logger,
            Function<String, String> replace
    ) throws IOException {
        File file = new File(targetFileName);
        if (file.exists()) {
            return;
        }
        logger.lifecycle("Creating file " + targetFileName);

        String template = IOUtils.toString(inputStream);
        IOUtils.toFile(replace.apply(template), file);
    }
}
