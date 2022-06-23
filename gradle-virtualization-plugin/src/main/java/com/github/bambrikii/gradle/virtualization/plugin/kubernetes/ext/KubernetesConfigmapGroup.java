package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

import groovy.lang.Closure;
import lombok.Getter;
import lombok.Setter;
import org.gradle.util.ConfigureUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KubernetesConfigmapGroup {
    private String name;
    private List<KubernetesConfigMap> configmaps = new ArrayList<>();

    public void configmap(Closure<ArrayList<KubernetesConfigMap>> closure) {
        configmaps.add(ConfigureUtil.configure(closure, new KubernetesConfigMap()));
    }
}
