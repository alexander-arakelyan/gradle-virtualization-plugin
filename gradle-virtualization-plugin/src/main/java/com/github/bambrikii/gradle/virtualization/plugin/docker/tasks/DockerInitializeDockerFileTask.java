package com.github.bambrikii.gradle.virtualization.plugin.docker.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils;
import lombok.SneakyThrows;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.DOCKER_DIR;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.DOCKER_FILE;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareDirs;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareFile;

public class DockerInitializeDockerFileTask extends AbstractExecTask<DockerInitializeDockerFileTask> {
    public DockerInitializeDockerFileTask() {
        super(DockerInitializeDockerFileTask.class);
    }

    @SneakyThrows
    @TaskAction
    public void exec() {
        Logger logger = getLogger();
        Path path = DockerUtils.getDockerDir(getWorkingDir());

        if (!prepareDirs(path, logger)) {
            return;
        }

        prepareFile(this, DockerInitializeDockerFileTask.class, DOCKER_DIR, DOCKER_FILE, this::prepareTemplate);
    }

    private String prepareTemplate(String template) {
        Project project = getProject();
        return template
                .replaceAll("\\$\\{app-name}", project.getName())
                .replaceAll("\\$\\{app-version}", project.getVersion().toString());
    }
}
