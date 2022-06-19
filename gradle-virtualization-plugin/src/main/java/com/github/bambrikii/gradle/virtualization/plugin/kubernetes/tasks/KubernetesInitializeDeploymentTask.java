package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import lombok.SneakyThrows;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareDirs;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareFile;

public class KubernetesInitializeDeploymentTask extends AbstractExecTask<KubernetesInitializeDeploymentTask> {
    public static final String DEPLOYMENT = "deployment.yaml";

    public KubernetesInitializeDeploymentTask() {
        super(KubernetesInitializeDeploymentTask.class);
    }

    @SneakyThrows
    @TaskAction
    public void exec() {
        Project project = getProject();
        Path path = Paths.get(getWorkingDir().getAbsolutePath(), "/src/main/kubernetes");
        Logger logger = getLogger();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        prepareDirs(path, logger);
        prepareFile(
                path,
                DEPLOYMENT,
                KubernetesInitializeDeploymentTask.class.getResourceAsStream(DEPLOYMENT),
                logger,
                template -> template
                        .replaceAll("\\$\\{app-namespace\\}", ext.getNamespace())
                        .replaceAll("\\$\\{app-name\\}", project.getName())
                        .replaceAll("\\$\\{app-version\\}", project.getVersion().toString())
        );
    }
}
