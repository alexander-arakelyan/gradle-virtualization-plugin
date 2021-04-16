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

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.buildRemoteRepoTag;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureDockerBuildDir;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureTagName;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.readImageId;
import static org.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

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
    Path dockerBuildDir = ensureDockerBuildDir(project, ext.getDockerBuildDir());
    String tagName = ext.getTagName();
    String repo = ext.getRepo();
    String namespace = ext.getRepoNamespace();

    String imageId = readImageId(project, dockerBuildDir);
    String component = ensureTagName(project, tagName);

//    String image = commit(dockerCommand, imageId, component, "latest");
    tag(dockerCommand, imageId, buildRemoteRepoTag(repo, namespace, component, version));
    tag(dockerCommand, imageId, buildRemoteRepoTag(repo, namespace, component, "latest"));
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

    logCommand(getLogger(), getCommandLine());

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

    logCommand(getLogger(), getCommandLine());

    super.exec();
  }
}

