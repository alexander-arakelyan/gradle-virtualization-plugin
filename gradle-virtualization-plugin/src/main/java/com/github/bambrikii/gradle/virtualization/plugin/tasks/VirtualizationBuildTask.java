package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.DOCKER_BUILD;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.DOCKER_LOGIN;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.DOCKER_TAG;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_CREATE_CONFIGMAPS;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_CREATE_SECRETS;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_DELETE_CONFIGMAPS;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesTaskUtils.KUBERNETES_DELETE_SECRETS;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.execTask;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.tryExecTask;

public class VirtualizationBuildTask extends DefaultTask {
    @TaskAction
    public void build() {
        TaskContainer tasks = getProject().getTasks();

        execTask(tasks, DOCKER_LOGIN);

        tryExecTask(tasks, KUBERNETES_DELETE_SECRETS, getLogger());
        execTask(tasks, KUBERNETES_CREATE_SECRETS);

        tryExecTask(tasks, KUBERNETES_DELETE_CONFIGMAPS, getLogger());
        execTask(tasks, KUBERNETES_CREATE_CONFIGMAPS);

        execTask(tasks, DOCKER_BUILD);
        execTask(tasks, DOCKER_TAG);
    }
}
