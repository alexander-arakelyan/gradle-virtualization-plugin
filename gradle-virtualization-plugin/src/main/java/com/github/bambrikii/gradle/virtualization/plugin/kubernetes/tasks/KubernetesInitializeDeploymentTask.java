package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils;
import lombok.SneakyThrows;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.DEPLOYMENT_FILE;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.getKubernetesDefaultFile;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareDirs;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareFile;

public class KubernetesInitializeDeploymentTask extends AbstractExecTask<KubernetesInitializeDeploymentTask> {
    public KubernetesInitializeDeploymentTask() {
        super(KubernetesInitializeDeploymentTask.class);
    }

    @SneakyThrows
    @TaskAction
    public void exec() {
        Project project = getProject();
        Path path = KubernetesUtils.getKubernetesFilesPath(getWorkingDir());
        Logger logger = getLogger();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        prepareDirs(path, logger);
        prepareFile(
                getKubernetesDefaultFile(getWorkingDir(), ext),
                KubernetesInitializeDeploymentTask.class.getResourceAsStream(DEPLOYMENT_FILE),
                logger,
                template -> createNamespace(project, ext, template)
        );
    }

    private String createNamespace(Project project, KubernetesExtension ext, String template) {
        return template
                .replaceAll("\\$\\{app-namespace}", KubernetesUtils.getDefaultNamespace(ext))
                .replaceAll("\\$\\{app-name}", project.getName())
                .replaceAll("\\$\\{app-version}", project.getVersion().toString());
    }
}
