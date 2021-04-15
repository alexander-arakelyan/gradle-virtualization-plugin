package org.bambrikii.gradle.virtualization.plugin.tasks;

import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.apache.commons.lang.StringUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.DOCKER_IID;

@Setter
public class DockerBuildTask extends AbstractExecTask<DockerBuildTask> {
  private String dockerCommand;
  private String dockerFile;
  private Path dockerSrcDir;
  private Path dockerBuildDir;

  public DockerBuildTask() {
    super(DockerBuildTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);

    args.add("build");
    String dockerSrcDirAbsolutePath = dockerSrcDir.toString();
    args.add(dockerSrcDirAbsolutePath);

    if (!StringUtils.isBlank(dockerFile)) {
      args.add("--file");
      args.add(dockerFile);
    }

    args.add("--iidfile");
    args.add(dockerBuildDir.toString() + "/" + DOCKER_IID);

    commandLine(args);

    super.exec();
  }
}
