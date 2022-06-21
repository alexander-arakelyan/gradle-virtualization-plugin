package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class KubernetesUtils {
    public static final String KUBERNETES_DIR = "src/main/kubernetes";
    public static final String DEPLOYMENT_FILE = "deployment.yaml";
    public static final String SERVICE_FILE = "service.yaml";
    public static final String DEFAULT_NAMESPACE = "default";

    private KubernetesUtils() {
    }

    public static void clusterCommand(KubernetesExtension ext, List<String> args) {
        String cmd = ext.getKubernetesClusterCommand();
        args.add(StringUtils.isEmpty(cmd) ? "minikube" : cmd);
    }

    public static void command(KubernetesExtension ext, List<String> args) {
        String cmd = ext.getKubernetesCommand();
        args.add(StringUtils.isEmpty(cmd) ? "kubectl" : cmd);
    }

    public static void namespace(KubernetesExtension ext, List<String> args) {
        String namespace = ext.getNamespace();
        if (StringUtils.isEmpty(namespace)) {
            return;
        }
        args.add("-n");
        args.add(namespace);
    }

    public static Path getKubernetesDir(File workingDir) {
        return Paths.get(workingDir.getAbsolutePath(), KUBERNETES_DIR);
    }

    public static String getDefaultNamespace(KubernetesExtension ext) {
        String namespace = ext.getNamespace();
        return StringUtils.isEmpty(namespace) ? DEFAULT_NAMESPACE : namespace;
    }
}
