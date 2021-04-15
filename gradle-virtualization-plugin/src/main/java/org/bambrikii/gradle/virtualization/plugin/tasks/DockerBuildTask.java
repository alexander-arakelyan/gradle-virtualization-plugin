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

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.DOCKER_IID;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureDockerBuildDir;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureDockerSrcDir;
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

    String dockerCommand = DockerUtils.getDockerCommand(ext.getDockerCommand());

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);

    args.add("build");

    Path dockerSrcDir = ensureDockerSrcDir(project, ext.getDockerSrcDir());
    String dockerSrcDirAbsolutePath = dockerSrcDir.toString();
    args.add(dockerSrcDirAbsolutePath);

    String dockerFile = ext.getDockerFile();
    if (!isBlank(dockerFile)) {
      args.add("--file");
      args.add(dockerFile);
    }

    Path dockerBuildDir = ensureDockerBuildDir(project, ext.getDockerBuildDir());
    args.add("--iidfile");
    args.add(dockerBuildDir.toString() + "/" + DOCKER_IID);

    commandLine(args);

    super.exec();
  }
}
