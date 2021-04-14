package org.bambrikii.gradle.container.plugin.utils;

import org.bambrikii.gradle.container.plugin.DockerExtension;
import org.gradle.api.Project;
import org.gradle.internal.impldep.org.apache.commons.io.IOUtils;
import org.gradle.internal.impldep.org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DockerUtils {
  public static final String DEFAULT_DOCKER_COMMAND = "docker";
  public static final String DOCKER_IID = "docker.iid";

  private DockerUtils() {
  }

  public static String getDockerCommand(DockerExtension extension) {
    String dockerCommand = extension.getCommand();
    return StringUtils.isBlank(dockerCommand) ? DEFAULT_DOCKER_COMMAND : dockerCommand;
  }

  public static String getRepo(DockerExtension extension, Project project) {
    String version = (String) project.getVersion();
    String snapshotUrl = extension.getSnapshotRepo();
    String releaseUrl = extension.getReleaseRepo();
    boolean rel = !StringUtils.endsWith(version, "-SNAPSHOT");
    String repoUrl = rel ? releaseUrl : snapshotUrl;
    return repoUrl;
  }

  public static Path ensureBuildDir(Project project, DockerExtension extension) {
    String dockerBuildDir = extension.getBuildDir();

    Path path = StringUtils.isBlank(dockerBuildDir)
            ? Paths.get(project.getBuildDir().getAbsolutePath(), "docker") // create docker default build dir
            : Paths.get(dockerBuildDir) // create docker custom build dir
            ;
    path = path.toAbsolutePath();

    File buildFile = path.toAbsolutePath().toFile();
    if (!buildFile.exists()) {
      buildFile.mkdirs();
    }
    return path;
  }

  public static String readImageId(Project project, Path buildDir) {
    String fileName = buildDir.toString() + "/" + DOCKER_IID;
    File file = new File(fileName);
    if (!file.exists()) {
      String message = "Failed to find file containing image id [" + fileName + "]";
      project.getLogger().lifecycle(message);
      throw new IllegalArgumentException(message);
    }
    try (InputStream inputStream = new FileInputStream(file);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream)
    ) {
      return IOUtils.toString(inputStreamReader);
    } catch (IOException ex) {
      String message = "Failed to read file image id file [" + fileName + "]";
      project.getLogger().lifecycle(message + ": " + ex.getMessage());
      throw new RuntimeException(message, ex);
    }
  }

  public static String ensureTagName(Project project, String tagName) {
    String tagName2 = StringUtils.isBlank(tagName)
            ? project.getName()
            : tagName;
    return tagName2;
  }
}
