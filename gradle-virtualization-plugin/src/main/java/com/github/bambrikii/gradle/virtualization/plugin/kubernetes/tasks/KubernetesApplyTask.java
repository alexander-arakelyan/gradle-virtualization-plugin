package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.command;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.namespace;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesApplyTask extends AbstractExecTask<KubernetesApplyTask> {
    public KubernetesApplyTask() {
        super(KubernetesApplyTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        List<String> args = new ArrayList<>();

        command(ext, args);
        namespace(ext, args);
        apply(ext, args);

        commandLine(args);

        logCommand(getLogger(), getCommandLine());

        super.exec();
    }

    private void apply(KubernetesExtension ext, List<String> args) {
        args.add("apply");
        args.add("-f");
        args.add(KubernetesUtils.getKubernetesDefaultFile(getWorkingDir(), ext));
    }
}
