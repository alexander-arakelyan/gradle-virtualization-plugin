package com.github.bambrikii.gradle.virtualization.plugin.docker.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerEnvVar;
import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerMount;
import com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils;
import com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.addDockerCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.buildContainerName;
import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

public class DockerRunTask extends AbstractExecTask<DockerRunTask> {
  public DockerRunTask() {
    super(DockerRunTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    String version = project.getVersion().toString();
    DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);


    List<String> args = new ArrayList<>();
    addDockerCommand(args, ext);
    args.add("run");
    addMounts(args, ext);
    addEnvs(args, ext);
    addContainerName(args, project, ext);
    addImageId(args, project, ext);

    commandLine(args);

    LogUtils.logCommand(getLogger(), getCommandLine());

    super.exec();
  }

  private void addMounts(List<String> args, DockerExtension ext) {
    List<DockerMount> mounts = ext.getMounts();
    if (mounts == null || mounts.isEmpty()) {
      return;
    }
    for (DockerMount mount : mounts) {
      args.add("-v");
      StringBuilder sb = new StringBuilder()
              .append("\"")
              .append(mount.getHost())
              .append(",")
              .append(mount.getContainer());
      if (!isBlank(mount.getOptions())) {
        sb.append(",").append(mount.getOptions());
      }
      sb.append("\"");
      args.add(sb.toString());
    }
  }

  private void addEnvs(List<String> args, DockerExtension ext) {
    List<DockerEnvVar> envs = ext.getEnvs();
    if (envs == null || envs.isEmpty()) {
      return;
    }
    for (DockerEnvVar env : envs) {
      args.add("-e");
      StringBuilder sb = new StringBuilder()
              .append("\"")
              .append(env.getName())
              .append(":")
              .append(env.getValue())
              .append("\"");
      args.add(sb.toString());
    }
  }

  private void addContainerName(List<String> args, Project project, DockerExtension ext) {
    args.add("--name");
    args.add(buildContainerName(project, ext));
  }

  private void addImageId(List<String> args, Project project, DockerExtension ext) {
    String tagName = ext.getTagName();
    String namespace = ext.getNamespace();
    String component = DockerUtils.ensureTagName(project, tagName);
    String localTagName = DockerUtils.buildLocalTag(namespace, component);

    args.add(localTagName);
  }
}
