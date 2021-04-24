package com.github.bambrikii.gradle.virtualization.plugin.utils;

import com.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import org.gradle.api.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

public class DockerUtils {
  public static final String DEFAULT_DOCKER_COMMAND = "docker";
  public static final String DOCKER_IID = "docker.iid";
  public static final String LATEST = "latest";

  private DockerUtils() {
  }

  public static String getDockerCommand(DockerExtension ext) {
    String command = ext.getDockerCommand();
    return isBlank(command) ? DEFAULT_DOCKER_COMMAND : command;
  }

  public static Path ensureDockerSrcDir(Project project, DockerExtension ext) {
    String dockerSrcDir = ext.getDockerSrcDir();
    Path path = isBlank(dockerSrcDir)
            ? Paths.get(project.getProjectDir().getAbsolutePath())
            : Paths.get(dockerSrcDir).toAbsolutePath();

    ensureDirs(path.toAbsolutePath());
    return path;
  }

  public static Path ensureDockerBuildDir(Project project, DockerExtension ext) {
    String dockerBuildDir = ext.getDockerBuildDir();
    Path path = isBlank(dockerBuildDir)
            ? Paths.get(project.getBuildDir().getAbsolutePath(), "docker") // create docker default build dir
            : Paths.get(dockerBuildDir).toAbsolutePath() // create docker custom build dir
            ;

    ensureDirs(path);
    return path;
  }

  private static void ensureDirs(Path path2) {
    File buildFile = path2.toFile();
    if (buildFile.exists()) {
      return;
    }
    buildFile.mkdirs();
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
      String imageId = IOUtils.toString(inputStreamReader);
      return imageId.substring(imageId.indexOf(":") + 1);
    } catch (IOException ex) {
      String message = "Failed to read file image id file [" + fileName + "]";
      project.getLogger().lifecycle(message + ": " + ex.getMessage());
      throw new RuntimeException(message, ex);
    }
  }

  public static String ensureTagName(Project project, String tagName) {
    return !isBlank(tagName)
            ? tagName
            : project.getName();
  }

  public static String buildLocalTag(String namespace, String component) {
    return buildLocalTag(namespace, component, LATEST);
  }

  public static String buildLocalTag(String namespace, String component, String version) {
    return namespace + "/" + component + ":" + version;
  }

  public static String buildRemoteRepoTag(String repo, String namespace, String component) {
    return buildRemoteRepoTag(repo, namespace, component, LATEST);
  }

  public static String buildRemoteRepoTag(String repo, String namespace, String component, String version) {
    return repo + "/" + namespace + "/" + component + ":" + version;
  }

  public static String extractRepo(DockerExtension ext, String version) {
    String snapshotRepo = ext.getSnapshotRepo();
    String repo = ext.getRepo();
    return isSnapshotVersion(version) && !isBlank(snapshotRepo)
            ? snapshotRepo
            : repo;
  }

  public static boolean isSnapshotVersion(String version) {
    return version.endsWith("-SNAPSHOT");
  }

  public static String buildContainerName(Project project, DockerExtension ext) {
    String containerName = ext.getContainerName();
    return !isBlank(containerName)
            ? containerName
            : project.getName();
  }
}
