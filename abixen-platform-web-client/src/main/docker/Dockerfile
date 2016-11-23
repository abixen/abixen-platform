FROM java:8
VOLUME /tmp
ADD abixen-platform.war app.war
RUN bash -c 'touch /app.war'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=cloud","-Dabixen.services.eureka.uri=discovery","-Dabixen.services.gateway.uri=gateway","-jar","/app.war"]