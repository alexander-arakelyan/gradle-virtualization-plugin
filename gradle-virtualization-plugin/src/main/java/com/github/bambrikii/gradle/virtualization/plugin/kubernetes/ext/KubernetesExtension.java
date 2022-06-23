package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

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
    @Inject
    private List<KubernetesSecretGroup> secretGroups = new ArrayList<>();
    @Inject
    private List<KubernetesConfigmapGroup> configmapGroups = new ArrayList<>();

    public void resources(Closure<ArrayList<String>> closure) {
        ConfigureUtil.configure(closure, resources);
    }

    public void secretGroups(Closure<ArrayList<KubernetesSecretGroup>> closure) {
        ConfigureUtil.configure(closure, secretGroups);
    }

    public void secretGroup(Closure<KubernetesSecretGroup> closure) {
        secretGroups.add(ConfigureUtil.configure(closure, new KubernetesSecretGroup()));
    }

    public void configmapGroups(Closure<ArrayList<KubernetesConfigmapGroup>> closure) {
        ConfigureUtil.configure(closure, secretGroups);
    }

    public void configmapGroup(Closure<KubernetesConfigmapGroup> closure) {
        configmapGroups.add(ConfigureUtil.configure(closure, new KubernetesConfigmapGroup()));
    }
}
