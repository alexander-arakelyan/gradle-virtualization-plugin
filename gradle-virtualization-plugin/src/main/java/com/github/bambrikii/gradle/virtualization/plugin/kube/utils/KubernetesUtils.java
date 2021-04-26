package com.github.bambrikii.gradle.virtualization.plugin.kube.utils;

import com.github.bambrikii.gradle.virtualization.plugin.VirtualizationPlugin;
import com.github.bambrikii.gradle.virtualization.plugin.kube.extensions.KubernetesExtension;

import java.util.Locale;

import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

public class KubernetesUtils {
  public static final String KUBERNETES_LOGIN_SECRET_NAME = VirtualizationPlugin.class.getName().toLowerCase(Locale.ROOT); // regcred

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

  public static String buildSecretName(KubernetesExtension ext) {
    String secretName = ext.getDockerSecretName();
    return !isBlank(secretName)
            ? secretName
            : KUBERNETES_LOGIN_SECRET_NAME;
  }
}
