package com.github.bambrikii.gradle.virtualization.plugin.kube.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kube.extensions.KubernetesExtension;

import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

public class KubernetesUtils {
  private KubernetesUtils() {
  }

  public static String buildKubeClusterCommand(KubernetesExtension ext) {
    String cmd = ext.getKubernetesClusterCommand();
    return !isBlank(cmd) ? cmd : "minikube";
  }

  public static String buildKubeCommand(KubernetesExtension ext) {
    String cmd = ext.getKubernetesCommand();
    return !isBlank(cmd) ? cmd : "kubectl";
  }
}
