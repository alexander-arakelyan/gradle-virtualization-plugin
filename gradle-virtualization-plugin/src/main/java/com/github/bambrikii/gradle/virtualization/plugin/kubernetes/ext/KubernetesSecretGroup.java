package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

import groovy.lang.Closure;
import lombok.Getter;
import lombok.Setter;
import org.gradle.util.ConfigureUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KubernetesSecretGroup {
    private String name;
    private List<KubernetesSecret> secrets = new ArrayList<>();

    public void secret(Closure<ArrayList<KubernetesSecret>> closure) {
        secrets.add(ConfigureUtil.configure(closure, new KubernetesSecret()));
    }
}
