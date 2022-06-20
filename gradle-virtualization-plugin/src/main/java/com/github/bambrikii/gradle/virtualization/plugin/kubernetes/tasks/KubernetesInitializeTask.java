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
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.KUBERNETES_DIR;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.SERVICE_FILE;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareDirs;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.TemplateUtils.prepareFile;

public class KubernetesInitializeTask extends AbstractExecTask<KubernetesInitializeTask> {
    public KubernetesInitializeTask() {
        super(KubernetesInitializeTask.class);
    }

    @SneakyThrows
    @TaskAction
    public void exec() {
        Path path = KubernetesUtils.getKubernetesDir(getWorkingDir());
        Logger logger = getLogger();

        if (!prepareDirs(path, logger)) {
            return;
        }

        prepareFile(this, KubernetesInitializeTask.class, KUBERNETES_DIR, DEPLOYMENT_FILE, this::prepareTemplate);
        prepareFile(this, KubernetesInitializeTask.class, KUBERNETES_DIR, SERVICE_FILE, this::prepareTemplate);
    }

    private String prepareTemplate(String template) {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);
        return template
                .replaceAll("\\$\\{app-namespace}", KubernetesUtils.getDefaultNamespace(ext))
                .replaceAll("\\$\\{app-name}", project.getName())
                .replaceAll("\\$\\{app-version}", project.getVersion().toString());
    }
}
