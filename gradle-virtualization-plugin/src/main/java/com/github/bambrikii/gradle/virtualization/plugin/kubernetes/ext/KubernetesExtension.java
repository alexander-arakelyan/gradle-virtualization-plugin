package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

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
    private String namespace;
    @Inject
    private String resource;
    @Inject
    private String dockerConfig;
}
