FROM openjdk:8-jre
VOLUME /tmp
ADD abixen-platform-core.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-Dabixen.services.eureka.uri=discovery"]
CMD ["-jar","/app.jar"]
