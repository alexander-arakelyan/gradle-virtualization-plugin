package org.bambrikii.gradle.virtualization.plugin.extensions;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

@Getter
@Setter
public class DockerExtension {
  @Inject
  private String dockerCommand;
  @Inject
  private String repo;
  @Inject
  private String username;
  @Inject
  private String password;
  @Inject
  private String repoNamespace;
  @Inject
  private String dockerFile;
  @Inject
  private String dockerSrcDir;
  @Inject
  private String dockerBuildDir;
  @Inject
  private String imageName;
  @Inject
  private String tagName;
}
