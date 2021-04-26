package com.github.bambrikii.gradle.virtualization.plugin.kube.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kube.extensions.KubernetesExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kube.utils.KubernetesUtils.buildKubeCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;
import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

public class KubernetesDeleteSecretTask extends AbstractExecTask<KubernetesDeleteSecretTask> {
  public KubernetesDeleteSecretTask() {
    super(KubernetesDeleteSecretTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

    deleteSecret(ext);
  }

  private void deleteSecret(KubernetesExtension ext) {
    List<String> args = Arrays.asList(
            buildKubeCommand(ext),
            "delete", "secret", "regcred"
    );

    commandLine(args);

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }

  public static String buildDockerConfigFileName(KubernetesExtension ext) {
    String config = ext.getDockerConfig();
    Path path = !isBlank(config)
            ? Paths.get(config)
            : Paths.get(System.getProperty("user.home"), ".docker/config.json");
    return path.toAbsolutePath().toString();
  }
}
