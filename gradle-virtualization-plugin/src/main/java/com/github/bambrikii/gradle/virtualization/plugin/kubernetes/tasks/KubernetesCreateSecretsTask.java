package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesSecret;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesSecretGroup;
import com.github.bambrikii.gradle.virtualization.plugin.utils.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.internal.ExecException;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.command;
import static com.github.bambrikii.gradle.virtualization.plugin.kubernetes.utils.KubernetesUtils.namespace;
import static com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils.logCommand;

public class KubernetesCreateSecretsTask extends AbstractExecTask<KubernetesCreateSecretsTask> {
    public KubernetesCreateSecretsTask() {
        super(KubernetesCreateSecretsTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        KubernetesExtension ext = project.getExtensions().getByType(KubernetesExtension.class);

        List<KubernetesSecretGroup> secretGroups = ext.getSecretGroups();
        if (secretGroups == null || secretGroups.isEmpty()) {
            return;
        }

        secretGroups.forEach(secret -> createSecret(ext, secret));
    }

    private void createSecret(KubernetesExtension ext, KubernetesSecretGroup secretGroup) {
        List<String> args = new ArrayList<>();
        command(ext, args);
        args.add("create");
        args.add("secret");
        args.add("generic");
        args.add(secretGroup.getName());

        secretGroup.getSecrets().forEach(secret -> {
            tryAddLiteral(secret, args);
            tryAddFile(secret, args);
            tryAddType(secret, args);
        });

        namespace(ext, args);

        commandLine(args);
        logCommand(getLogger(), getCommandLine());

        try {
            super.exec();
        } catch (ExecException ex) {
            getLogger().lifecycle(ex.getMessage());
        }
    }

    private void tryAddLiteral(KubernetesSecret secret, List<String> args) {
        String literal = secret.getLiteral();
        if (StringUtils.isEmpty(literal)) {
            return;
        }

        StringBuilder sb = buildValue("--from-literal=", literal, secret.getName());
        args.add(sb.toString());
    }

    private void tryAddFile(KubernetesSecret secret, List<String> args) {
        String file = secret.getFile();
        if (StringUtils.isEmpty(file)) {
            return;
        }
        StringBuilder sb = buildValue("--from-file=", file, secret.getName());
        args.add(sb.toString());
    }

    private void tryAddType(KubernetesSecret secret, List<String> args) {
        String type = secret.getType();
        if (StringUtils.isEmpty(type)) {
            return;
        }
        args.add("--type=" + type);
    }

    private StringBuilder buildValue(String str, String literal, String key) {
        StringBuilder sb = new StringBuilder(str);
        if (!StringUtils.isEmpty(key)) {
            sb.append(key).append("=");
        }
        sb.append(literal);
        return sb;
    }
}
