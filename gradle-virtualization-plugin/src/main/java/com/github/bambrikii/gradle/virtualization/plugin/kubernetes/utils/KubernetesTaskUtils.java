package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesApplyTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesCreateConfigmapsTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesCreateNamespaceTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesCreateSecretsTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesDeleteConfigmapsTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesDeleteNamespaceTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesDeleteSecretsTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesDeleteTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesGetPodsTask;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks.KubernetesInitializeTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.VirtualizationTaskUtils.VIRTUALIZATION_GROUP;

public class KubernetesTaskUtils {
    public static final String KUBERNETES_INITIALIZE = "kubernetesInitialize";
    public static final String KUBERNETES_CREATE_SECRETS = "kubernetesCreateSecrets";
    public static final String KUBERNETES_DELETE_SECRETS = "kubernetesDeleteSecrets";
    public static final String KUBERNETES_CREATE_CONFIGMAPS = "kubernetesCreateConfigmaps";
    public static final String KUBERNETES_DELETE_CONFIGMAPS = "kubernetesDeleteConfigmaps";
    public static final String KUBERNETES_APPLY = "kubernetesApply";
    public static final String KUBERNETES_DELETE = "kubernetesDelete";
    public static final String KUBERNETES_GET_PODS = "kubernetesGetPods";

    public static final String KUBERNETES_CREATE_NAMESPACE = "kubernetesCreateNamespace";
    public static final String KUBERNETES_DELETE_NAMESPACE = "kubernetesDeleteNamespace";

    private KubernetesTaskUtils() {
    }

    public static void registerKubeExtension(ExtensionContainer extensions) {
        extensions.create("kubernetes", KubernetesExtension.class);
    }

    public static void registerKubeTasks(Project project, TaskContainer tasks) {
        tasks.register(KUBERNETES_CREATE_SECRETS, KubernetesCreateSecretsTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_DELETE_SECRETS, KubernetesDeleteSecretsTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_CREATE_CONFIGMAPS, KubernetesCreateConfigmapsTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_DELETE_CONFIGMAPS, KubernetesDeleteConfigmapsTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_GET_PODS, KubernetesGetPodsTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_APPLY, KubernetesApplyTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_DELETE, KubernetesDeleteTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_INITIALIZE, KubernetesInitializeTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));

        tasks.register(KUBERNETES_CREATE_NAMESPACE, KubernetesCreateNamespaceTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
        tasks.register(KUBERNETES_DELETE_NAMESPACE, KubernetesDeleteNamespaceTask.class, task -> task.setGroup(VIRTUALIZATION_GROUP));
    }
}
