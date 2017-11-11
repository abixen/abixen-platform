echo staring mvn build

IF EXIST "pom.xml" (
  mvn clean package -DskipDocker
  echo installed mvn
) ELSE (
    ECHO failed
)
start /b java -jar .\abixen-platform-eureka\target\abixen-platform-eureka.jar
start /b java -jar .\abixen-platform-configuration\target\abixen-platform-configuration.jar
start /b java -jar -Dspring.profiles.active=dev abixen-platform-gateway/target/abixen-platform-gateway.jar
start /b java -jar -Dspring.profiles.active=dev abixen-platform-business-intelligence-service/target/abixen-platform-business-intelligence-service.jar
start /b java -jar -Dspring.profiles.active=dev abixen-platform-web-content-service/target/abixen-platform-web-content-service.jar
start /b java -jar -Dspring.profiles.active=dev abixen-platform-core/target/abixen-platform-core.jar
start /b java -jar -Dspring.profiles.active=dev abixen-platform-web-client/target/abixen-platform.war
