package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesSecret;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.command;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.namespace;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesDeleteSecretsTask extends AbstractExecTask<KubernetesDeleteSecretsTask> {
    public KubernetesDeleteSecretsTask() {
        super(KubernetesDeleteSecretsTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        List<KubernetesSecret> secrets = ext.getSecrets();
        if (secrets == null || secrets.isEmpty()) {
            return;
        }

        secrets.forEach(secret -> deleteSecret(ext, secret));
    }

    private void deleteSecret(KubernetesExtension ext, KubernetesSecret secret) {
        List<String> args = new ArrayList<>();
        command(ext, args);
        args.add("delete");
        args.add("secret");
        args.add(secret.getName());

        namespace(ext, args);

        commandLine(args);
        logCommand(getLogger(), getCommandLine());

        super.exec();
    }
}
