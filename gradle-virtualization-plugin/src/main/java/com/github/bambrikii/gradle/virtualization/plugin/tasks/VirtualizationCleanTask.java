package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.DOCKER_CONTAINER_RM;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_DELETE_CONFIGMAPS;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_DELETE_NAMESPACE;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_DELETE_SECRETS;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.tryExecTask;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.tryWithDocker;

public class VirtualizationCleanTask extends DefaultTask {
    @TaskAction
    public void clean() {
        Project project = getProject();
        TaskContainer tasks = project.getTasks();

        Logger logger = getLogger();

        tryWithDocker(unused -> tryExecTask(tasks, DOCKER_CONTAINER_RM, logger), project);

        tryExecTask(tasks, KUBERNETES_DELETE_CONFIGMAPS, logger);
        tryExecTask(tasks, KUBERNETES_DELETE_SECRETS, logger);

        tryExecTask(tasks, KUBERNETES_DELETE_NAMESPACE, logger);
    }
}
