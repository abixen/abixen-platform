FROM openjdk:8-jre
VOLUME /tmp
VOLUME /tmp
ADD abixen-platform-zipkin.jar app.jar

EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-Dabixen.services.eureka.uri=discovery"]
CMD ["-jar","/app.jar"]
