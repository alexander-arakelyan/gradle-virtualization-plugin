package org.bambrikii.gradle.container.plugin;

import lombok.Data;

@Data
public class DockerExtension {
  private String command;
  private String releaseRepo;
  private String snapshotRepo;
  private String username;
  private String password;
  private String dockerFile;
  private String buildDir;
  private String imageName;
  private String tagName;
}
