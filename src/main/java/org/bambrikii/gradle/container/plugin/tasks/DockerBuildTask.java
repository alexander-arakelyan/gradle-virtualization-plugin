package org.bambrikii.gradle.container.plugin.tasks;

import org.bambrikii.gradle.container.plugin.DockerExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.apache.commons.lang.StringUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.DOCKER_IID;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.ensureBuildDir;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.getDockerCommand;

public class DockerBuildTask extends AbstractExecTask<DockerBuildTask> {
  public DockerBuildTask() {
    super(DockerBuildTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension extension = this.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = getDockerCommand(extension);
    String dockerFile = extension.getDockerFile();

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);

    Path buildDir = ensureBuildDir(project, extension);
    String buildDirAbsolutePath = buildDir.toFile().getAbsolutePath();

    args.add("build");
    String dockerBuildDir = extension.getBuildDir();
    if (!StringUtils.isBlank(dockerBuildDir)) {
      args.add(buildDirAbsolutePath);
    }

    if (!StringUtils.isBlank(dockerFile)) {
      args.add("--file");
      args.add(dockerFile);
    }

    args.add("--iidfile");
    args.add(buildDirAbsolutePath + "/" + DOCKER_IID);

    commandLine(args);

    super.exec();
  }
}
