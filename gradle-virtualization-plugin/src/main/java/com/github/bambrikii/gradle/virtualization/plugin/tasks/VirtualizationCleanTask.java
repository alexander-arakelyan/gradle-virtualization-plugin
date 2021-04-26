package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.DOCKER_CONTAINER_RM;
import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesTaskUtils.KUBERNETES_DELETE_SECRET;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.tryExecTask;

public class VirtualizationCleanTask extends DefaultTask {
  @TaskAction
  public void clean() {
    TaskContainer tasks = getProject().getTasks();

    tryExecTask(tasks, DOCKER_CONTAINER_RM, getLogger());
    tryExecTask(tasks, KUBERNETES_DELETE_SECRET, getLogger());
  }
}
