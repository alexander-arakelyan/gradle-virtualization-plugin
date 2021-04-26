package com.github.bambrikii.gradle.virtualization.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.registerDockerExtension;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.registerDockerTasks;
import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesTaskUtils.registerKubeExtension;
import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesTaskUtils.registerKubeTasks;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.registerVirtualizationExtension;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.registerVirtualizationTasks;

public class VirtualizationPlugin implements Plugin<Project> {
  public void apply(Project project) {
    ExtensionContainer extensions = project.getExtensions();
    TaskContainer tasks = project.getTasks();
    ObjectFactory objects = project.getObjects();

    // Docker
    registerDockerExtension(extensions);
    registerDockerTasks(project, tasks);

    // Kubernetes
    registerKubeExtension(extensions);
    registerKubeTasks(project, tasks);

    // Virtualization
    registerVirtualizationExtension(extensions);
    registerVirtualizationTasks(project, tasks);
  }
}
