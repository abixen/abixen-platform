#!/bin/sh

type=$1

eureka_url=http://configuration:8761


if [ "$type" = "count_components" ];then
    c=$(curl -s  -H "Accept: application/json" $eureka_url/eureka/apps|jq '.applications.application|length') 
    echo "services count=$c"
else
  exit 1
fi

