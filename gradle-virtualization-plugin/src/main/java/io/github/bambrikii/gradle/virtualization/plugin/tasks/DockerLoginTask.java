package io.github.bambrikii.gradle.virtualization.plugin.tasks;

import io.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import io.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static io.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.extractRepo;
import static io.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.getDockerCommand;
import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

@Setter
public class DockerLoginTask extends AbstractExecTask<DockerLoginTask> {

  public DockerLoginTask() {
    super(DockerLoginTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    String version = project.getVersion().toString();
    DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = getDockerCommand(ext.getDockerCommand());
    String username = ext.getUsername();
    String password = ext.getPassword();
    String repo = extractRepo(ext, version);

    if (isBlank(repo)) {
      throw new IllegalArgumentException("Docker repo required!");
    }

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

    List<String> commandLine = new ArrayList<>(getCommandLine());
    commandLine.set(commandLine.size() - 2, "<secret>");
    LogUtils.logCommand(getLogger(), commandLine);

    super.exec();
  }
}
