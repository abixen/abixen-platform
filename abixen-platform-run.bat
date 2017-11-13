echo off
set var=%1

IF "%var%"=="start" (
   @echo start maven build
    call mvn clean package -DskipDocker
   @echo Maven build completed
) ELSE (
    echo Invalid option
	exit /b 0
)

@echo starting applications
call start /b java -jar .\abixen-platform-eureka\target\abixen-platform-eureka.jar
call start /b java -jar .\abixen-platform-configuration\target\abixen-platform-configuration.jar

echo %time% , sleep for 30 secs
timeout 30 > NUL
echo %time% , slept for 30 secs

echo Starting abixen-platform-core, abixen-platform-business-intelligence-service, abixen-platform-web-content-service, abixen-platform-web-client & abixen-platform-zipkin
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-gateway/target/abixen-platform-gateway.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-business-intelligence-service/target/abixen-platform-business-intelligence-service.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-web-content-service/target/abixen-platform-web-content-service.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-core/target/abixen-platform-core.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-web-client/target/abixen-platform.war
