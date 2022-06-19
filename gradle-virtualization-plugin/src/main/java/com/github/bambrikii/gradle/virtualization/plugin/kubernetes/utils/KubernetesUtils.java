package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.StringUtils;

import java.util.List;

public class KubernetesUtils {
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
}
