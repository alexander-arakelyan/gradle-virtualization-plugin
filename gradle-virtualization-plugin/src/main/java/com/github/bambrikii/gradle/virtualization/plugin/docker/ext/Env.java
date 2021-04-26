package com.github.bambrikii.gradle.virtualization.plugin.docker.ext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Env {
  private String name;
  private String value;
}
