package com.github.bambrikii.gradle.virtualization.plugin.utils;

import com.github.bambrikii.gradle.virtualization.plugin.extensions.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.DockerBuildTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.DockerContainerRmTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.DockerLoginTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.DockerPushTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.DockerRunTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.DockerTagTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.DockerUtils.getDockerCommand;

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

  public static DockerExtension registerDockerExtension(ExtensionContainer extensions) {
    return extensions.create("docker", DockerExtension.class);
  }

  public static void registerDockerTasks(Project project, TaskContainer tasks) {
    tasks.register("dockerLogin", DockerLoginTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    tasks.register("dockerBuild", DockerBuildTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    tasks.register("dockerTag", DockerTagTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    tasks.register("dockerPush", DockerPushTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    tasks.register("dockerRun", DockerRunTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    tasks.register("dockerContainerRm", DockerContainerRmTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
  }

  public static void addDockerCommand(List<String> args, DockerExtension ext) {
    args.add(getDockerCommand(ext));
  }
}
