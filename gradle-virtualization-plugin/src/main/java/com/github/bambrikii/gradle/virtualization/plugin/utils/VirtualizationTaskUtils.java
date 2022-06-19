package com.github.bambrikii.gradle.virtualization.plugin.utils;

import com.github.bambrikii.gradle.virtualization.plugin.tasks.VirtualizationBuildTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.VirtualizationCleanTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.VirtualizationDeployTask;
import com.github.bambrikii.gradle.virtualization.plugin.tasks.VirtualizationInitializeTask;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

public class VirtualizationTaskUtils {
    public static final String VIRTUALIZATION_GROUP = "virtualization";

    private VirtualizationTaskUtils() {
    }

    public static void registerVirtualizationExtension(ExtensionContainer ext) {

    }

    public static void registerVirtualizationTasks(Project project, TaskContainer tasks) {
        tasks.register("virtualizationClean", VirtualizationCleanTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register("virtualizationBuild", VirtualizationBuildTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register("virtualizationDeploy", VirtualizationDeployTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register("virtualizationInitialize", VirtualizationInitializeTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    }

    public static void tryExecTask(TaskContainer tasks, String name, Logger logger) {
        try {
            execTask(tasks, name);
        } catch (Throwable ex) {
            logger.debug(ex.getMessage(), ex);
        }
    }

    public static Task execTask(TaskContainer tasks, String name) {
        Task task = tasks.getByName(name);
        for (Action action : task.getActions()) {
            action.execute(task);
        }
        return task;
    }
}
