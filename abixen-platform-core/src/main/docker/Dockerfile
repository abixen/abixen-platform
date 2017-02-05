FROM openjdk:8-jre
VOLUME /tmp
ADD abixen-platform-core.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=cloud","-Dabixen.services.eureka.uri=discovery","-jar","/app.jar"]