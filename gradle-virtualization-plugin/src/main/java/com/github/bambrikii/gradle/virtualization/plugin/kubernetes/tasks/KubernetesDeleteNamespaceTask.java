package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.internal.ExecException;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.command;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesDeleteNamespaceTask extends AbstractExecTask<KubernetesDeleteNamespaceTask> {
    public KubernetesDeleteNamespaceTask() {
        super(KubernetesDeleteNamespaceTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        List<String> args = new ArrayList<>();

        command(ext, args);
        args.add("delete");
        args.add("namespace");
        args.add(KubernetesUtils.getDefaultNamespace(ext));

        commandLine(args);
        logCommand(getLogger(), getCommandLine());

        try {
            super.exec();
        } catch (ExecException ex) {
            getLogger().lifecycle(ex.getMessage());
        }
    }
}
