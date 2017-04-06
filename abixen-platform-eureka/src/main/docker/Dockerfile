FROM openjdk:8-jre
VOLUME /tmp
ADD abixen-platform-eureka.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dabixen.services.eureka.uri=discovery"]
CMD ["-jar","/app.jar"]
