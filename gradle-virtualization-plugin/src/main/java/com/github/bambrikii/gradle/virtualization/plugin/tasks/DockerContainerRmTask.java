package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.DockerTaskUtils.addDockerCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.buildContainerName;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class DockerContainerRmTask extends AbstractExecTask<DockerContainerRmTask> {
  public DockerContainerRmTask() {
    super(DockerContainerRmTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);


    List<String> args = new ArrayList<>();
    addDockerCommand(args, ext);
    args.add("container");
    args.add("rm");
    args.add(buildContainerName(project, ext));

    commandLine(args);

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }
}
