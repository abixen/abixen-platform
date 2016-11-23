FROM java:8
VOLUME /tmp
ADD abixen-platform-eureka.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dabixen.services.eureka.uri=discovery","-jar","/app.jar"]