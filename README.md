# Gradle Container Plugin

## Commands

1. `dockerLogin` -
1. `dockerBuild` -
1. `dockerTag` -
1. `dockerPush` -

## Configuration

1. `container`
1. `docker`
    1. `releaseRepo` - used if project version is release
    1. `snapshotRepo` - used if project version is snapshot
    1. `username` - docker login user
    1. `password` - docker login password
    1. `dockerFile` - docker file location
    1. `buildDir` - optional, by default project dir will be used to source files
    1. `tagName` - TODO:
    1. `imageName` - TODO:
