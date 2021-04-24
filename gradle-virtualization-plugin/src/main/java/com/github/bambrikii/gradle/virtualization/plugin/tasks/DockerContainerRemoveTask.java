package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils;
import com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

public class DockerContainerRemoveTask extends AbstractExecTask<DockerContainerRemoveTask> {
  public DockerContainerRemoveTask() {
    super(DockerContainerRemoveTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = DockerUtils.getDockerCommand(ext.getDockerCommand());

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("container");
    args.add("rm");
    args.add(project.getName());

    commandLine(args);

    LogUtils.logCommand(getLogger(), getCommandLine());

    super.exec();
  }

}
