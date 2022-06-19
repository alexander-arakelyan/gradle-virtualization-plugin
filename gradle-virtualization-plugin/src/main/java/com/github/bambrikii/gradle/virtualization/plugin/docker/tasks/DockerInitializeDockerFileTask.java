package com.github.bambrikii.gradle.virtualization.plugin.docker.tasks;

import lombok.SneakyThrows;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareDirs;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareFile;

public class DockerInitializeDockerFileTask extends AbstractExecTask<DockerInitializeDockerFileTask> {
    public static final String DOCKERFILE = "Dockerfile";

    public DockerInitializeDockerFileTask() {
        super(DockerInitializeDockerFileTask.class);
    }

    @SneakyThrows
    @TaskAction
    public void exec() {
        Project project = getProject();
        Path path = Paths.get(getWorkingDir().getAbsolutePath(), "/src/main/docker");
        Logger logger = getLogger();

        prepareDirs(path, logger);
        prepareFile(
                path,
                DOCKERFILE,
                DockerInitializeDockerFileTask.class.getResourceAsStream(DOCKERFILE),
                logger, template -> template
                        .replaceAll("\\$\\{app-name\\}", project.getName())
                        .replaceAll("\\$\\{app-version\\}", project.getVersion().toString())
        );
    }
}
