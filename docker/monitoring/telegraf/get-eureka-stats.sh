#!/bin/bash

type=$1

eureka_url=http://discovery:8761


if [ "$type" = "count_services" ];then
    c=$(curl -s  -H "Accept: application/json" $eureka_url/eureka/apps|jq '.applications.application|length') 
    echo "services count=$c"
else
  exit 1
fi

