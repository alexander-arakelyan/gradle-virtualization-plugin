package org.bambrikii.gradle.virtualization.plugin;

import org.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerTaskUtils.registerDockerExtension;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerTaskUtils.registerDockerTasks;
import static org.bambrikii.gradle.virtualization.plugin.utils.KubernetesTaskUtils.registerKubernetesExtension;

public class VirtualizationPlugin implements Plugin<Project> {
  public void apply(Project project) {
    ExtensionContainer extensions = project.getExtensions();
    TaskContainer tasks = project.getTasks();
    ObjectFactory objects = project.getObjects();

    // Kubernetes
    registerKubernetesExtension(extensions, objects);

    // Docker
    NamedDomainObjectContainer<DockerExtension> dockerExtension = registerDockerExtension(extensions, objects);
    registerDockerTasks(project, dockerExtension, tasks);
  }
}
