package org.bambrikii.gradle.virtualization.plugin.utils;

import org.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import org.bambrikii.gradle.virtualization.plugin.tasks.DockerBuildTask;
import org.bambrikii.gradle.virtualization.plugin.tasks.DockerLoginTask;
import org.bambrikii.gradle.virtualization.plugin.tasks.DockerPushTask;
import org.bambrikii.gradle.virtualization.plugin.tasks.DockerTagTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import java.nio.file.Path;

import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureDockerBuildDir;
import static org.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.ensureDockerSrcDir;

public class DockerTaskUtils {

  public static final String VIRTUALIZATION_GROUP = "virtualization";

  private DockerTaskUtils() {
  }

  public static NamedDomainObjectContainer<DockerExtension> registerDockerExtension(ExtensionContainer extensions, ObjectFactory objects) {
    NamedDomainObjectContainer<DockerExtension> dockerExtension = objects.domainObjectContainer(
            DockerExtension.class,
            name -> objects.newInstance(DockerExtension.class, name)
    );
    extensions.add("docker", dockerExtension);
    return dockerExtension;
  }

  public static void registerDockerTasks(Project project, NamedDomainObjectContainer<DockerExtension> dockerExtension, TaskContainer tasks) {
    tasks.register("dockerLogin2", DockerLoginTask.class, task -> {
      task.setGroup(VIRTUALIZATION_GROUP);
    });
    dockerExtension.all(ext -> {

      String dockerCommand = DockerUtils.getDockerCommand(ext.getDockerCommand().get());
      String username = ext.getUsername().get();
      String password = ext.getPassword().get();
      String repo = ext.getDockerRepo().get();
      String tagName = ext.getTagName().get();
      String imageName = ext.getImageName().get();
      String dockerFile = ext.getDockerFile().get();
      Path dockerSrcDir = ensureDockerSrcDir(project, ext.getDockerSrcDir().get());
      Path dockerBuildDir = ensureDockerBuildDir(project, ext.getDockerBuildDir().get());

      tasks.register("dockerLogin", DockerLoginTask.class, task -> {
        task.setGroup(VIRTUALIZATION_GROUP);

        task.setCommand(dockerCommand);
        task.setRepo(repo);
        task.setUsername(username);
        task.setPassword(password);
      });

      tasks.register("dockerBuild", DockerBuildTask.class, task -> {
        task.setGroup(VIRTUALIZATION_GROUP);

        task.setDockerCommand(dockerCommand);
        task.setDockerFile(dockerFile);
        task.setDockerSrcDir(dockerSrcDir);
        task.setDockerBuildDir(dockerBuildDir);
      });

      tasks.register("dockerTag", DockerTagTask.class, task -> {
        task.setGroup(VIRTUALIZATION_GROUP);

        task.setDockerCommand(dockerCommand);
        task.setTagName(tagName);
        task.setDockerBuildDir(dockerBuildDir);
      });

      tasks.register("dockerPush", DockerPushTask.class, task -> {
        task.setGroup(VIRTUALIZATION_GROUP);

        task.setDockerCommand(dockerCommand);
        task.setTagName(tagName);
        task.setTagName(imageName);
      });
    });
  }
}
