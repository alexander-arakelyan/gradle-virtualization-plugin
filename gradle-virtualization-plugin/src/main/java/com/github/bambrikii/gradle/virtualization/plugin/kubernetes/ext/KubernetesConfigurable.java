package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext;

public interface KubernetesConfigurable {
    String getName();

    String getLiteral();

    String getFile();

    String getType();

    void setName(String name);

    void setLiteral(String literal);

    void setFile(String file);

    void setType(String type);
}
