package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesApplyTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesCreateSecretTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesDeleteSecretTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesGetPodsTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesInitializeTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.VIRTUALIZATION_GROUP;

public class KubernetesTaskUtils {
    public static final String KUBERNETES_CREATE_SECRET = "kubernetesCreateSecret";
    public static final String KUBERNETES_DELETE_SECRET = "kubernetesDeleteSecret";
    public static final String KUBERNETES_GET_PODS = "kubernetesGetPods";
    public static final String KUBERNETES_APPLY = "kubernetesApply";
    public static final String KUBERNETES_INITIALIZE = "kubernetesInitialize";

    private KubernetesTaskUtils() {
    }

    public static void registerKubeExtension(ExtensionContainer extensions) {
        extensions.create("kubernetes", KubernetesExtension.class);
    }

    public static void registerKubeTasks(Project project, TaskContainer tasks) {
        tasks.register(KUBERNETES_CREATE_SECRET, KubernetesCreateSecretTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_DELETE_SECRET, KubernetesDeleteSecretTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_GET_PODS, KubernetesGetPodsTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_APPLY, KubernetesApplyTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_INITIALIZE, KubernetesInitializeTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    }
}
