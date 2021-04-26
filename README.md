# Gradle Virtualization Plugin

## Commands

### Aggregated Virtualization Commands

1. `virtualizationClean` - includes:
    1. `dockerContainerRm`
    1. `kubernetesDeleteSecret`
1. `virtualizationBuild` - includes:
    1. `dockerLogin`
    1. `kubernetesDeleteSecret`
    1. `kubernetesCreateSecret`
    1. `dockerBuild`
    1. `dockerTag`
1. `virtualizationDeploy` - includes:
    1. `dockerLogin`
    1. `kubernetesDeleteSecret`
    1. `kubernetesCreateSecret`
    1. `dockerBuild`
    1. `dockerTag`
    1. `dockerPush`
    1. `kubernetesApply`

### Docker Commands

1. `dockerLogin` - executes `docker login`
1. `dockerBuild` - executes `docker build`
1. `dockerTag` - executes `docker tag`
1. `dockerPush` - executes `docker push`
1. `dockerRun` - executes `docker run`
1. `dockerContainerRm` - executes `docker container rm`

### Kubernetes Commands

1. `kubernetesDeleteSecret` - executes `kubectl delete secret`
1. `kubernetesCreateSecret` - executes `kubectl create secret`
1. `kubernetesGetPods` - executes `minikube kubectl -- get po - A`
1. `kubernetesApply` - executes `kubectl apply`

## Configuration

1. `docker`
    1. `dockerCommand` - docker binary name, `docker` by default
    1. `repo` - remote repository name
    1. `snapshotRepo` - remote snapshot repository name, if there is one. Optional
    1. `username` - repository username
    1. `password` - repository password
    1. `namespace` - image namespace, used to tag image names
    1. `dockerFile` - docker file location
    1. `dockerSrcDir` - optional, by default `project.srcDir` will be used as a project source directory
    1. `dockerBuildDir` - optional, by default `project.buildDir` will be used to store temporary files
    1. `imageName` - custom image name. Optional, `project.name` by default
    1. `tagName` - custom tag name. Optional, `project.name` by default
    1. `mounts` -> mount settings to run container. Includes `host` + `container` + `options`
    1. `envs` -> environment variables to run container. Include `name` + `value`
1. `kubernetes`
    1. `kubernetesClusterCommand` - kubernetes cluster binary file name. Optional, `minikube` by default
    1. `kubernetesCommand` - kubernetes control binary file name. Optional, `kubectl` by default
    1. `resource` - yaml configuration file
    1. `dockerConfig` - docker JSON configuration file location. Might include remote repository authentication tokens,
       etc. Optional, `$USER_HOME/.docker/config.json` by default
    1. `dockerSecretName` - custom docker secret name. Optional,  

Please, find sample configuration in [build.gradle](gradle-virtualization-plugin-test/build.gradle#L31-L57) file of
gradle-virtualization-plugin-test project.
