package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.DOCKER_INITIALIZE_DOCKERFILE;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_INITIALIZE_DEPLOYMENT;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.tryExecTask;

public class VirtualizationInitializeTask extends DefaultTask {
    @TaskAction
    public void clean() {
        TaskContainer tasks = getProject().getTasks();

        tryExecTask(tasks, DOCKER_INITIALIZE_DOCKERFILE, getLogger());
        tryExecTask(tasks, KUBERNETES_INITIALIZE_DEPLOYMENT, getLogger());
    }
}
