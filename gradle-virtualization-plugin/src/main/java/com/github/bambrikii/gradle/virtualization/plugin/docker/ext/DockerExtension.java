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
  private List<Mount> mounts = new ArrayList<>();
  @Inject
  private List<Env> envs = new ArrayList<>();

  public void run(Closure<ArrayList<Env>> closure) {
    ConfigureUtil.configure(closure, envs);
  }

  public void mounts(Closure<ArrayList<Mount>> closure) {
    ConfigureUtil.configure(closure, mounts);
  }

  public void mount(Closure<Mount> closure) {
    mounts.add(ConfigureUtil.configure(closure, new Mount()));
  }

  public void envs(Closure<ArrayList<Env>> closure) {
    ConfigureUtil.configure(closure, envs);
  }

  public void env(Closure<ArrayList<Env>> closure) {
    envs.add(ConfigureUtil.configure(closure, new Env()));
  }
}
