package com.github.bambrikii.gradle.virtualization.plugin.docker.ext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mount {
  private String host;
  private String container;
  private String options;
}
