package org.bambrikii.gradle.virtualization.plugin.tasks;

import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.getDockerCommand;
import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

@Setter
public class DockerLoginTask extends AbstractExecTask<DockerLoginTask> {
  private String command;
  private String username;
  private String password;
  private String repo;

  public DockerLoginTask() {
    super(DockerLoginTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();

    String dockerCommand = getDockerCommand(command);

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("login");
    if (!isBlank(username)) {
      args.add("--username");
      args.add(username);
      args.add("--password");
      args.add(password);
    }
    args.add(repo);

    commandLine(args);

    super.exec();
  }
}
