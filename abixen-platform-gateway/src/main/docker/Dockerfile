FROM openjdk:8-jre
VOLUME /tmp
ADD abixen-platform-gateway.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-Dabixen.services.eureka.uri=discovery"]
CMD ["-jar","/app.jar"]
