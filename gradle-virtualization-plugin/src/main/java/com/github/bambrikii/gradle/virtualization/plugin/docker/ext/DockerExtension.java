package com.github.bambrikii.gradle.virtualization.plugin.docker.ext;

import groovy.lang.Closure;
import lombok.Getter;
import lombok.Setter;
import org.gradle.util.ConfigureUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DockerExtension {
  @Inject
  private String dockerCommand;
  @Inject
  private String repo;
  @Inject
  private String snapshotRepo;
  @Inject
  private List<DockerRegistry> repositories = new ArrayList<>();
  @Inject
  private String username;
  @Inject
  private String password;
  @Inject
  private String namespace;
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
  @Inject
  private String containerName;
  @Inject
  private List<DockerMount> mounts = new ArrayList<>();
  @Inject
  private List<DockerEnvVar> envs = new ArrayList<>();

  public void repositories(Closure<ArrayList<DockerRegistry>> closure) {
    ConfigureUtil.configure(closure, repositories);
  }

  public void repository(Closure<DockerRegistry> closure) {
    repositories.add(ConfigureUtil.configure(closure, new DockerRegistry()));
  }

  public void mounts(Closure<ArrayList<DockerMount>> closure) {
    ConfigureUtil.configure(closure, mounts);
  }

  public void mount(Closure<DockerMount> closure) {
    mounts.add(ConfigureUtil.configure(closure, new DockerMount()));
  }

  public void envs(Closure<ArrayList<DockerEnvVar>> closure) {
    ConfigureUtil.configure(closure, envs);
  }

  public void env(Closure<ArrayList<DockerEnvVar>> closure) {
    envs.add(ConfigureUtil.configure(closure, new DockerEnvVar()));
  }
}
