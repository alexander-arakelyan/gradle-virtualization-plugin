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
  }
}