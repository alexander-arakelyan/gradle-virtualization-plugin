package com.github.bambrikii.gradle.virtualization.plugin.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils;
import com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.buildLocalTag;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.buildRemoteRepoTag;

@Setter
public class DockerTagTask extends AbstractExecTask<DockerTagTask> {
  public DockerTagTask() {
    super(DockerTagTask.class);
  }

  @TaskAction
  public void exec() {
    Project project = getProject();
    String version = project.getVersion().toString();
    DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);

    String dockerCommand = DockerUtils.getDockerCommand(ext.getDockerCommand());
    Path dockerBuildDir = DockerUtils.ensureDockerBuildDir(project, ext.getDockerBuildDir());
    String tagName = ext.getTagName();
    String repo = DockerUtils.extractRepo(ext, version);
    String namespace = ext.getRepoNamespace();

    String imageId = DockerUtils.readImageId(project, dockerBuildDir);
    String component = DockerUtils.ensureTagName(project, tagName);


    tag(dockerCommand, imageId, buildLocalTag(namespace, component));
    tag(dockerCommand, imageId, buildLocalTag(namespace, component, version));

    tag(dockerCommand, imageId, buildRemoteRepoTag(repo, namespace, component));
    tag(dockerCommand, imageId, buildRemoteRepoTag(repo, namespace, component, version));
  }

  private String commit(String dockerCommand, String imageId, String component, final String version) {
    String image = component + ":" + version;

    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("container");
    args.add("commit");
    args.add(imageId);
    args.add(image);

    commandLine(args);

    LogUtils.logCommand(getLogger(), getCommandLine());

    super.exec();

    return image;
  }

  private void tag(String dockerCommand, String image, String tagName) {
    List<String> args = new ArrayList<>();
    args.add(dockerCommand);
    args.add("image");
    args.add("tag");
    args.add(image);
    args.add(tagName);

    commandLine(args);

    LogUtils.logCommand(getLogger(), getCommandLine());

    super.exec();
  }
}

