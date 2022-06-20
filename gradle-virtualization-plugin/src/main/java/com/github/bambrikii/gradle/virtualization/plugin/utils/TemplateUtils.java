package com.github.bambrikii.gradle.virtualization.plugin.utils;

import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.AbstractExecTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.IOUtils.PATH_SEPARATOR;

public class TemplateUtils {
    private TemplateUtils() {
    }

    public static boolean prepareDirs(Path path, Logger logger) {
        File dir = path.toFile();
        if (dir.exists()) {
            return false;
        }
        logger.lifecycle("Creating directory " + path);
        return dir.mkdirs();
    }

    public static void prepareFile(AbstractExecTask<?> task, Class<?> resource, String subPath, String fileName, Function<String, String> replace) throws IOException {
        File workingDir = task.getWorkingDir();
        String targetPath = workingDir.getAbsolutePath() + PATH_SEPARATOR + subPath + PATH_SEPARATOR + fileName;
        File targetFile = new File(targetPath);
        if (targetFile.exists()) {
            return;
        }

        task.getLogger().lifecycle("Creating file " + targetPath);

        String template = IOUtils.toString(resource.getResourceAsStream(fileName));
        IOUtils.toFile(replace.apply(template), targetFile);
    }
}
