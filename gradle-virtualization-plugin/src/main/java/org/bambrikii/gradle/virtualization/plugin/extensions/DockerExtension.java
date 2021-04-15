package org.bambrikii.gradle.virtualization.plugin.extensions;

import org.gradle.api.provider.Property;

import javax.inject.Inject;

//@Getter
//@Setter
public abstract class DockerExtension {
  private String dockerCommand;
  private String dockerRepo;
  private String username;
  private String password;
  private String dockerFile;
  private String dockerSrcDir;
  private String dockerBuildDir;
  private String imageName;
  private String tagName;

  @Inject
  public DockerExtension(
          String dockerCommand,
          String dockerRepo,
          String username,
          String password,
          String dockerFile,
          String dockerBuildDir,
          String imageName,
          String tagName
  ) {
    this.dockerCommand = dockerCommand;
    this.dockerRepo = dockerRepo;
    this.username = username;
    this.password = password;
    this.dockerFile = dockerFile;
    this.dockerBuildDir = dockerBuildDir;
    this.imageName = imageName;
    this.tagName = tagName;
  }

  abstract public Property<String> getDockerCommand();

  abstract public Property<String> getDockerRepo();

  abstract public Property<String> getUsername();

  abstract public Property<String> getPassword();

  abstract public Property<String> getDockerFile();

  abstract public Property<String> getDockerSrcDir();

  abstract public Property<String> getDockerBuildDir();

  abstract public Property<String> getImageName();

  abstract public Property<String> getTagName();
}
