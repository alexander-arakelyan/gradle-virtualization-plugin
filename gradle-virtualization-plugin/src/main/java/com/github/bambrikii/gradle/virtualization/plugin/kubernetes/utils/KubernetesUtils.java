package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesConfigurable;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.StringUtils;
import org.gradle.api.Project;

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

    public static void name(Project project, String name, List<String> args) {
        args.add(StringUtils.isEmpty(name) ? project.getName() : name);
    }

    public static void tryAddSecretLiteral(KubernetesConfigurable secret, List<String> args) {
        String literal = secret.getLiteral();
        if (StringUtils.isEmpty(literal)) {
            return;
        }

        StringBuilder sb = buildKeyValue("--from-literal=", literal, secret.getName());
        args.add(sb.toString());
    }

    public static void tryAddSecretFile(KubernetesConfigurable secret, List<String> args) {
        String file = secret.getFile();
        if (StringUtils.isEmpty(file)) {
            return;
        }
        StringBuilder sb = buildKeyValue("--from-file=", file, secret.getName());
        args.add(sb.toString());
    }

    public static void tryAddSecretType(KubernetesConfigurable secret, List<String> args) {
        String type = secret.getType();
        if (StringUtils.isEmpty(type)) {
            return;
        }
        args.add("--type=" + type);
    }

    public static StringBuilder buildKeyValue(String str, String literal, String key) {
        StringBuilder sb = new StringBuilder(str);
        if (!StringUtils.isEmpty(key)) {
            sb.append(key).append("=");
        }
        sb.append(literal);
        return sb;
    }
}
