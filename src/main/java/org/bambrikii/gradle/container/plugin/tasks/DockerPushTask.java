package org.bambrikii.gradle.container.plugin.tasks;

import org.bambrikii.gradle.container.plugin.DockerExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.ensureTagName;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.getDockerCommand;

public class DockerPushTask extends AbstractExecTask<DockerPushTask> {
  public DockerPushTask() {
    super(DockerPushTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension extension = this.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = getDockerCommand(extension);
    String tagName = extension.getTagName();

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("image");
    args.add("push");
    args.add(ensureTagName(project, tagName));

    commandLine(args);

    super.exec();
  }
}
