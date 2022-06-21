package com.github.bambrikii.gradle.virtualization.plugin.docker.utils;

import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerBuildTask;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerContainerRmTask;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerInitializeTask;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerLoginTask;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerPushTask;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerRunTask;
import com.github.bambrikii.gradle.virtualization.plugin.docker.tasks.DockerTagTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.getDockerCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.VIRTUALIZATION_GROUP;

public class DockerTaskUtils {
    public static final String DOCKER_LOGIN = "dockerLogin";
    public static final String DOCKER_BUILD = "dockerBuild";
    public static final String DOCKER_TAG = "dockerTag";
    public static final String DOCKER_PUSH = "dockerPush";
    public static final String DOCKER_RUN = "dockerRun";
    public static final String DOCKER_CONTAINER_RM = "dockerContainerRm";
    public static final String DOCKER_INITIALIZE = "dockerInitialize";

    private DockerTaskUtils() {
    }

    public static DockerExtension registerDockerExtension(ExtensionContainer extensions) {
        return extensions.create("docker", DockerExtension.class);
    }

    public static void registerDockerTasks(Project project, TaskContainer tasks) {
        tasks.register(DOCKER_LOGIN, DockerLoginTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(DOCKER_BUILD, DockerBuildTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(DOCKER_TAG, DockerTagTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(DOCKER_PUSH, DockerPushTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(DOCKER_RUN, DockerRunTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(DOCKER_CONTAINER_RM, DockerContainerRmTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(DOCKER_INITIALIZE, DockerInitializeTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    }

    public static void addDockerCommand(List<String> args, DockerExtension ext) {
        args.add(getDockerCommand(ext));
    }
}
