FROM java:8
VOLUME /tmp
ADD abixen-platform-gateway.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=cloud","-Dabixen.services.eureka.uri=discovery","-jar","/app.jar"]