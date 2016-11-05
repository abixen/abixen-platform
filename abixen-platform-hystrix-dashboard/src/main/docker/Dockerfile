FROM java:8
VOLUME /tmp
ADD abixen-platform-hystrix-dashboard.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8989
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=cloud","-Dabixen.services.eureka.uri=discovery","-jar","/app.jar"]