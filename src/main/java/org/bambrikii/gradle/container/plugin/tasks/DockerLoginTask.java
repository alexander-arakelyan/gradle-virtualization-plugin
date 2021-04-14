package org.bambrikii.gradle.container.plugin.tasks;

import org.bambrikii.gradle.container.plugin.DockerExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.getDockerCommand;
import static org.bambrikii.gradle.container.plugin.utils.DockerUtils.getRepo;

public class DockerLoginTask extends AbstractExecTask<DockerLoginTask> {
  public DockerLoginTask() {
    super(DockerLoginTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension extension = this.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = getDockerCommand(extension);
    String username = extension.getUsername();
    String password = extension.getPassword();
    String repoUrl = getRepo(extension, project);

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("login");
    if (!StringUtils.isBlank(username)) {
      args.add("--username");
      args.add(username);
      args.add("--password");
      args.add(password);
    }
    args.add(repoUrl);

    commandLine(args);

    super.exec();
  }
}
