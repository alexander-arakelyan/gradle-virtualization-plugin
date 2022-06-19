package com.github.bambrikii.gradle.virtualization.plugin.docker.ext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DockerRegistry {
  private String url;
  private String username;
  private String password;
  private boolean active = true;
}
