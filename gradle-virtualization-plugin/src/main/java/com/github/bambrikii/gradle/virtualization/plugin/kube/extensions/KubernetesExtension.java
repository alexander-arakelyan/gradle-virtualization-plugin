package com.github.bambrikii.gradle.virtualization.plugin.kube.extensions;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

@Getter
@Setter
public class KubernetesExtension {
  @Inject
  public KubernetesExtension() {
  }

  @Inject
  private String kubernetesClusterCommand;
  @Inject
  private String kubernetesCommand;
  @Inject
  private String resource;
  @Inject
  private String dockerConfig;
}
