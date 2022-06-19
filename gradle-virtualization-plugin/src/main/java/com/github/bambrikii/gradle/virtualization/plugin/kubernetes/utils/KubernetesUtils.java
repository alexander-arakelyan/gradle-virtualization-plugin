package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.utils.IOUtils.PATH_SEPARATOR;

public class KubernetesUtils {
    public static final String DEPLOYMENT_FILE = "deployment.yaml";
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

    public static Path getKubernetesFilesPath(File workingDir) {
        return Paths.get(workingDir.getAbsolutePath(), "/src/main/kubernetes");
    }

    public static String getKubernetesDefaultFile(File workingDir, KubernetesExtension ext) {
        String resource = ext.getResource();
        return StringUtils.isEmpty(resource) ? getKubernetesFilesPath(workingDir) + PATH_SEPARATOR + DEPLOYMENT_FILE : resource;
    }

    public static String getDefaultNamespace(KubernetesExtension ext) {
        String namespace = ext.getNamespace();
        return StringUtils.isEmpty(namespace) ? DEFAULT_NAMESPACE : namespace;
    }
}
