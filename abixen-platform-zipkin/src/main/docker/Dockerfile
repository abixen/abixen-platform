FROM java:8
VOLUME /tmp
VOLUME /tmp
ADD abixen-platform-zipkin.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dabixen.services.eureka.uri=discovery","-jar","/app.jar"]