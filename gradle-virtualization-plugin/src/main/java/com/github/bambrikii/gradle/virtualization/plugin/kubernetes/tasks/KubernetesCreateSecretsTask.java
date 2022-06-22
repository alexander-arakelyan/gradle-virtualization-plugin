package com.github.bambrikii.gradle.virtualization.plugin.kubernetes.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesExtension;
import com.github.bambrikii.gradle.virtualization.plugin.kubernetes.ext.KubernetesSecret;
import com.github.bambrikii.gradle.virtualization.plugin.utils.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

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

        List<KubernetesSecret> secrets = ext.getSecrets();
        if (secrets == null || secrets.isEmpty()) {
            return;
        }

        secrets.forEach(secret -> createSecret(ext, secret));
    }

    private void createSecret(KubernetesExtension ext, KubernetesSecret secret) {
        List<String> args = new ArrayList<>();
        command(ext, args);
        args.add("create");
        args.add("secret");
        args.add("generic");
        args.add(secret.getName());

        tryAddLiteral(secret, args);
        tryAddFile(secret, args);
        tryAddType(secret, args);

        namespace(ext, args);

        commandLine(args);
        logCommand(getLogger(), getCommandLine());

        super.exec();
    }

    private void tryAddLiteral(KubernetesSecret secret, List<String> args) {
        String literal = secret.getLiteral();
        if (StringUtils.isEmpty(literal)) {
            return;
        }
        args.add("--from-literal=" + literal);
    }

    private void tryAddFile(KubernetesSecret secret, List<String> args) {
        String file = secret.getFile();
        if (StringUtils.isEmpty(file)) {
            return;
        }
        args.add("--from-file=" + file);
    }

    private void tryAddType(KubernetesSecret secret, List<String> args) {
        String type = secret.getType();
        if (StringUtils.isEmpty(type)) {
            return;
        }
        args.add("--type=" + type);
    }
}
