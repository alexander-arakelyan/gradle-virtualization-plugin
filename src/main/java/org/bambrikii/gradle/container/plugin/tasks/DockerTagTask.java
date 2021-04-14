package org.bambrikii.gradle.container.plugin.tasks;

import org.bambrikii.gradle.container.plugin.DockerExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.ensureBuildDir;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.ensureTagName;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.getDockerCommand;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.readImageId;

public class DockerTagTask extends AbstractExecTask<DockerTagTask> {
  public DockerTagTask() {
    super(DockerTagTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension extension = this.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = getDockerCommand(extension);
    String tagName = extension.getTagName();

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("tag");
    Path buildDir = ensureBuildDir(project, extension);
    args.add(readImageId(project, buildDir));
    args.add(ensureTagName(project, tagName));

    commandLine(args);

    super.exec();
  }
}

