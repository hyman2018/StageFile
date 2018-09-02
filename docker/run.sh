#!/usr/bin/env bash

REPO=platform
CONTAINER=filestage

source $1
if [ "$#" -ne 2 ];then
  echo "Erorr, can't open envfile: $1"
  echo "Usage: $0 <envfile> <tags>"
  echo "e.g., $0 dev.env xxxxxx"
  exit 1
else
  envfile=$1
  TAG=$2
  echo "# Using envfile: $envfile"
fi

DOCKER_IMAGE=$REPO/$CONTAINER:$TAG
echo $DOCKER_IMAGE

globalConf="
  -v /etc/localtime:/etc/localtime \
  -m 20g \
  --restart always \
"

moduleConf="
  -p 8080:8080 \
  -p 9000:9000 \
  --env-file $1 \
"

docker rm -f -v $CONTAINER
cmd="docker run -d --name $CONTAINER \
  $globalConf \
  $moduleConf \
  $DOCKER_IMAGE \
"
echo $cmd
eval $cmd