#!/bin/bash

docker ps --filter "label=com.abixen.service" --format '{{.Label "com.abixen.service"}} {{.Label "com.abixen.service.type"}}' \
  | sort \
  | uniq -c \
  | while read count service type;do
     echo "abixen_servicecount,name=$service,type=$type count=$count"
    done
