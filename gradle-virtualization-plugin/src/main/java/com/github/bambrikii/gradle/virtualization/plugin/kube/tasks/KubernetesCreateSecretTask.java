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

public class KubernetesCreateSecretTask extends AbstractExecTask<KubernetesCreateSecretTask> {
  public KubernetesCreateSecretTask() {
    super(KubernetesCreateSecretTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

    createSecret(ext);
  }

  private void createSecret(KubernetesExtension ext) {
    List<String> args = Arrays.asList(
            buildKubeCommand(ext),
            "create", "secret", "generic", "regcred",
            "--from-file=.dockerconfigjson=" + buildDockerConfigFileName(ext),
            "--type=kubernetes.io/dockerconfigjson"
    );

    commandLine(args);

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }

  private String buildDockerConfigFileName(KubernetesExtension ext) {
    String config = ext.getDockerConfig();
    Path path = !isBlank(config)
            ? Paths.get(config)
            : Paths.get(System.getProperty("user.home"), ".docker/config.json");
    return path.toAbsolutePath().toString();
  }
}
