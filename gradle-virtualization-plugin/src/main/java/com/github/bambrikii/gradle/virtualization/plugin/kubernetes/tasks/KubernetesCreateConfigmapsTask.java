package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesConfigmapGroup;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.internal.ExecException;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.command;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.namespace;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.tryAddSecretFile;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.tryAddSecretLiteral;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.tryAddSecretType;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesCreateConfigmapsTask extends AbstractExecTask<KubernetesCreateConfigmapsTask> {
    public KubernetesCreateConfigmapsTask() {
        super(KubernetesCreateConfigmapsTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        List<KubernetesConfigmapGroup> configmapGroups = ext.getConfigmapGroups();
        if (configmapGroups == null || configmapGroups.isEmpty()) {
            return;
        }

        configmapGroups.forEach(configmap -> create(ext, configmap));
    }

    private void create(KubernetesExtension ext, KubernetesConfigmapGroup secretGroup) {
        List<String> args = new ArrayList<>();
        command(ext, args);
        args.add("create");
        args.add("configmap");
        args.add(secretGroup.getName());

        secretGroup.getConfigmaps().forEach(configmap -> {
            tryAddSecretLiteral(configmap, args);
            tryAddSecretFile(configmap, args);
            tryAddSecretType(configmap, args);
        });

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
