package com.github.bambrikii.gradle.virtualization.plugin.docker.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerExtension;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.addDockerCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.DOCKER_IID;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.ensureDockerBuildDir;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.ensureDockerSrcDir;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;
import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

@Setter
public class DockerBuildTask extends AbstractExecTask<DockerBuildTask> {
  public DockerBuildTask() {
    super(DockerBuildTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);

    List<String> args = new ArrayList<>();

    addDockerCommand(args, ext);
    args.add("build");
    addSrcDir(project, ext, args);
    addDockerFile(ext, args);
    addOutputImageIdFile(project, ext, args);

    commandLine(args);

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }

  private void addSrcDir(Project project, DockerExtension ext, List<String> args) {
    Path dockerSrcDir = ensureDockerSrcDir(project, ext);
    args.add(dockerSrcDir.toString());
  }

  private void addDockerFile(DockerExtension ext, List<String> args) {
    String dockerFile = ext.getDockerFile();
    if (isBlank(dockerFile)) {
      return;
    }
    args.add("--file");
    args.add(dockerFile);
  }

  private void addOutputImageIdFile(Project project, DockerExtension ext, List<String> args) {
    args.add("--iidfile");
    Path dockerBuildDir = ensureDockerBuildDir(project, ext);
    args.add(dockerBuildDir.toString() + "/" + DOCKER_IID);
  }
}
