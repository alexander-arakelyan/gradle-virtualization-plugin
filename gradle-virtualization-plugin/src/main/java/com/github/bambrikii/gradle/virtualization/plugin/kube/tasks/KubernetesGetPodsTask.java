package com.github.bambrikii.gradle.virtualization.plugin.kube.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kube.extensions.KubernetesExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.Arrays;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesUtils.buildKubeClusterCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesUtils.buildKubeCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesGetPodsTask extends AbstractExecTask<KubernetesGetPodsTask> {
  public KubernetesGetPodsTask() {
    super(KubernetesGetPodsTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

    List<String> args = Arrays.asList(
            buildKubeClusterCommand(ext),
            buildKubeCommand(ext),
            "--", "get", "po", "-A"
    );

    commandLine(args);

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }
}
