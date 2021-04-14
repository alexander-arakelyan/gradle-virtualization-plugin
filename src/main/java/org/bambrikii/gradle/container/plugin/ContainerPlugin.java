package org.bambrikii.gradle.container.plugin;

import org.bambrikii.gradle.container.plugin.tasks.DockerBuildTask;
import org.bambrikii.gradle.container.plugin.tasks.DockerLoginTask;
import org.bambrikii.gradle.container.plugin.tasks.DockerPushTask;
import org.bambrikii.gradle.container.plugin.tasks.DockerTagTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

public class ContainerPlugin implements Plugin<Project> {
  public void apply(Project project) {
    project.getExtensions().create("container", ContainerExtension.class);
    project.getExtensions().create("docker", DockerExtension.class);

    TaskContainer tasks = project.getTasks();
    tasks.register("dockerLogin", DockerLoginTask.class, new DockerLoginTask());
    tasks.register("dockerBuild", DockerLoginTask.class, new DockerBuildTask());
    tasks.register("dockerTag", DockerLoginTask.class, new DockerTagTask());
    tasks.register("dockerPush", DockerLoginTask.class, new DockerPushTask());
  }
}
