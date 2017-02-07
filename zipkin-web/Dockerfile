FROM openjdk:8-jre

EXPOSE 9412
COPY lib/ /zipkin-web/

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom"]
CMD ["-jar","/zipkin-web/zipkin-web-all.jar", "-zipkin.web.port=:9412", "-zipkin.web.rootUrl=/", "-zipkin.web.query.dest=zipkin:9411"]

