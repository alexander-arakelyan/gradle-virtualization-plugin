package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.extensions.Env;
import com.github.bambrikii.gradle.virtualization.plugin.extensions.Mount;
import com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils;
import com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

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

    String dockerCommand = DockerUtils.getDockerCommand(ext.getDockerCommand());

    String tagName = ext.getTagName();
    String namespace = ext.getRepoNamespace();
    String component = DockerUtils.ensureTagName(project, tagName);

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("run");
    args.add(DockerUtils.buildLocalTag(namespace, component));
    addMounts(args, ext.getMounts());
    addEnvs(args, ext.getEnvs());

    commandLine(args);

    LogUtils.logCommand(getLogger(), getCommandLine());

    super.exec();
  }

  private void addMounts(List<String> args, List<Mount> mounts) {
    if (mounts == null || mounts.isEmpty()) {
      return;
    }
    for (Mount mount : mounts) {
      args.add("-v");
      StringBuilder sb = new StringBuilder();
      if (!isBlank(mount.getHost())) {
        sb.append(mount.getHost()).append(":");
      }
      sb.append(mount.getContainer());
      if (!isBlank(mount.getOptions())) {
        sb.append(":").append(mount.getOptions());
      }
      args.add(sb.toString());
    }
  }

  private void addEnvs(List<String> args, List<Env> envs) {
    if (envs == null || envs.isEmpty()) {
      return;
    }
    for (Env env : envs) {
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
}
