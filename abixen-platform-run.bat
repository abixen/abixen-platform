ECHO staring mvn build
call mvn clean package -DskipDocker
ECHO "Maven build completed...starting applications"
call start /b java -jar .\abixen-platform-eureka\target\abixen-platform-eureka.jar
call start /b java -jar .\abixen-platform-configuration\target\abixen-platform-configuration.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-gateway/target/abixen-platform-gateway.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-business-intelligence-service/target/abixen-platform-business-intelligence-service.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-web-content-service/target/abixen-platform-web-content-service.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-core/target/abixen-platform-core.jar
call start /b java -jar -Dspring.profiles.active=dev abixen-platform-web-client/target/abixen-platform.war
