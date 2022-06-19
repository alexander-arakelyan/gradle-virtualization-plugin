package com.github.bambrikii.gradle.virtualization.plugin.docker.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerExtension;
import lombok.SneakyThrows;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.DOCKERFILE;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.getDockerFile;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareDirs;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareFile;

public class DockerInitializeDockerFileTask extends AbstractExecTask<DockerInitializeDockerFileTask> {
    public DockerInitializeDockerFileTask() {
        super(DockerInitializeDockerFileTask.class);
    }

    @SneakyThrows
    @TaskAction
    public void exec() {
        Project project = getProject();
        DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);
        Logger logger = getLogger();
        Path path = Paths.get(getWorkingDir().getAbsolutePath(), "/src/main/docker");

        prepareDirs(path, logger);
        prepareFile(
                getDockerFile(getWorkingDir(), ext),
                DockerInitializeDockerFileTask.class.getResourceAsStream(DOCKERFILE),
                logger, template -> template
                        .replaceAll("\\$\\{app-name}", project.getName())
                        .replaceAll("\\$\\{app-version}", project.getVersion().toString())
        );
    }
}
