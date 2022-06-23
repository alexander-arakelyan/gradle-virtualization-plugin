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
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.name;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.namespace;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesDeleteConfigmapsTask extends AbstractExecTask<KubernetesDeleteConfigmapsTask> {
    public KubernetesDeleteConfigmapsTask() {
        super(KubernetesDeleteConfigmapsTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        List<KubernetesConfigmapGroup> configmapGroups = ext.getConfigmapGroups();
        if (configmapGroups == null || configmapGroups.isEmpty()) {
            return;
        }

        configmapGroups.forEach(configmapGroup -> delete(ext, configmapGroup));
    }

    private void delete(KubernetesExtension ext, KubernetesConfigmapGroup group) {
        List<String> args = new ArrayList<>();
        command(ext, args);
        args.add("delete");
        args.add("configmap");

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
