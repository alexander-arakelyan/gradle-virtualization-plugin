package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerRegistry;
import groovy.lang.Closure;
import lombok.Getter;
import lombok.Setter;
import org.gradle.util.ConfigureUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    @Inject
    private List<String> resources = new ArrayList<>();

    public void repositories(Closure<ArrayList<String>> closure) {
        ConfigureUtil.configure(closure, resources);
    }
}
