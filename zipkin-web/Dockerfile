FROM java:8

EXPOSE 9412
COPY lib/ /zipkin-web/

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/zipkin-web/zipkin-web-all.jar", "-zipkin.web.port=:9412", "-zipkin.web.rootUrl=/", "-zipkin.web.query.dest=zipkin:9411"]
