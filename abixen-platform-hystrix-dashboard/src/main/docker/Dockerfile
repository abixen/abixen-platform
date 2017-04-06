FROM openjdk:8-jre
VOLUME /tmp
ADD abixen-platform-hystrix-dashboard.jar app.jar

EXPOSE 8989
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-Dabixen.services.eureka.uri=discovery"]
CMD ["-jar","/app.jar"]
