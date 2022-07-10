package com.github.bambrikii.gradle.virtualization.plugin.docker.tasks;

import com.github.bambrikii.gradle.virtualization.plugin.docker.ext.DockerExtension;
import com.github.bambrikii.gradle.virtualization.plugin.utils.LogUtils;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerTaskUtils.addDockerCommand;
import static com.github.bambrikii.gradle.virtualization.plugin.docker.utils.DockerUtils.extractRepo;
import static org.codehaus.groovy.runtime.StringGroovyMethods.isBlank;

@Setter
public class DockerLoginTask extends AbstractExecTask<DockerLoginTask> {
    public DockerLoginTask() {
        super(DockerLoginTask.class);
    }

    @TaskAction
    public void exec() {
        Project project = getProject();
        String version = project.getVersion().toString();
        DockerExtension ext = project.getExtensions().getByType(DockerExtension.class);

        List<String> args = new ArrayList<>();
        addDockerCommand(args, ext);
        args.add("login");
        boolean credentials = addCredentials(args, ext);
        addRepo(args, version, ext);

        commandLine(args);

        List<String> commandLine = new ArrayList<>(getCommandLine());
        if (credentials) {
            commandLine.set(commandLine.size() - 2, "<secret>");
        }
        LogUtils.logCommand(getLogger(), commandLine);

        super.exec();
    }

    private boolean addCredentials(List<String> args, DockerExtension ext) {
        String username = ext.getUsername();
        if (isBlank(username)) {
            return false;
        }

        args.add("--username");
        args.add(username);

        String password = ext.getPassword();
        args.add("--password");
        args.add(password);
        return true;
    }

    private void addRepo(List<String> args, String version, DockerExtension ext) {
        String repo = extractRepo(ext, version);

        if (isBlank(repo)) {
            throw new IllegalArgumentException("Docker repo required!");
        }
        args.add(repo);
    }
}
