package org.bambrikii.gradle.virtualization.plugin.tasks;

import lombok.Setter;
import org.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureDockerBuildDir;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureTagName;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.readImageId;

@Setter
public class DockerTagTask extends AbstractExecTask<DockerTagTask> {
  public DockerTagTask() {
    super(DockerTagTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension ext = this.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = DockerUtils.getDockerCommand(ext.getDockerCommand());
    Path dockerBuildDir = ensureDockerBuildDir(project, ext.getDockerBuildDir());
    String tagName = ext.getTagName();

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("tag");
    args.add(readImageId(project, dockerBuildDir));
    args.add(ensureTagName(project, tagName));

    commandLine(args);

    super.exec();
  }
}

