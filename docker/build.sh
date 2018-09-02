#!/usr/bin/env bash

REPO=platform
CONTAINER=filestage
TAG=test3
#$(git rev-parse --short HEAD)
DOCKER_IMAGE=$REPO/$CONTAINER:$TAG

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BUILDROOT=$DIR/../
cd $BUILDROOT

#git rev-parse --short HEAD > docker/buildno

export MAVEN_OPTS="-Xms256m -Xmx512m -Xss10m"
mvn clean install -D maven.test.skip=true

# Build docker
cmd="docker build -t $DOCKER_IMAGE -f $DIR/Dockerfile $BUILDROOT"
echo $cmd
eval $cmd