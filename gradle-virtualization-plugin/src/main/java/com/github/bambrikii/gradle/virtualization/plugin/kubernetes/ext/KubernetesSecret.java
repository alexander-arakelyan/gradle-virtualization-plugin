package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

import lombok.Getter;
import lombok.Setter;

/**
 * https://kubernetes.io/docs/tasks/configmap-secret/managing-secret-using-kubectl/
 */
@Getter
@Setter
public class KubernetesSecret {
    private String name;
    private String literal;
    private String file;
    private String type;
}
