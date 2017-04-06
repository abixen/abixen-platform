FROM telegraf:1.2-alpine

COPY get-eureka-stats.sh /usr/local/bin/
COPY get-abixen-container-stats.sh /usr/local/bin/
RUN apk --no-cache add curl jq bash docker
