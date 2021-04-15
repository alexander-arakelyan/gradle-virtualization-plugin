package org.bambrikii.gradle.virtualization.plugin.tasks;

import lombok.Setter;
import org.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureTagName;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.readImageId;

@Setter
public class DockerTagTask extends AbstractExecTask<DockerTagTask> {
  private String dockerCommand;
  private String tagName;
  private Path dockerBuildDir;

  public DockerTagTask() {
    super(DockerTagTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension extension = this.getExtensions().getByType(DockerExtension.class);

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("tag");
    args.add(readImageId(project, dockerBuildDir));
    args.add(ensureTagName(project, tagName));

    commandLine(args);

    super.exec();
  }
}

