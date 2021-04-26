package com.github.bambrikii.gradle.virtualization.plugin.kube.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kube.extensions.KubernetesExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesUtils.buildKubeCommand;
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

    args.add(buildKubeCommand(ext));
    args.add("apply");
    args.add("-f");
    args.add(ext.getResource());

    commandLine(args);

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }
}
