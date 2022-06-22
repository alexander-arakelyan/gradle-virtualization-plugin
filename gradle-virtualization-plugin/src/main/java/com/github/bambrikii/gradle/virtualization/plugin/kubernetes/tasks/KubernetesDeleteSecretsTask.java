package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesSecretGroup;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.internal.ExecException;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.command;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.name;
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

        List<KubernetesSecretGroup> secretGroups = ext.getSecretGroups();
        if (secretGroups == null || secretGroups.isEmpty()) {
            return;
        }

        secretGroups.forEach(secret -> deleteSecret(ext, secret));
    }

    private void deleteSecret(KubernetesExtension ext, KubernetesSecretGroup group) {
        List<String> args = new ArrayList<>();
        command(ext, args);
        args.add("delete");
        args.add("secret");

        String name = group.getName();
        name(getProject(), name, args);

        namespace(ext, args);

        commandLine(args);
        logCommand(getLogger(), getCommandLine());

        try {
            super.exec();
        } catch (ExecException ex) {
            getLogger().lifecycle(ex.getMessage());
        }
    }
}
