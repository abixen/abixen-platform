#Usage: sh abixen-run.sh start

operation=$1

if [ "$operation" == "start" ] || [ "$operation" == "" ]; then
	echo "Abixen-run start operation..."
else
	echo "Incorrect operation provided in argument. Possible values: 'start'"
	exit
fi
echo 'Starting maven build ...'

if mvn clean install -DskipDocker; then
	echo "Project Build completed successfully...!!!"
else
	echo "Something went wrong."
	exit 1
fi

nohup java -jar abixen-platform-eureka/target/abixen-platform-eureka.jar &
nohup java -jar abixen-platform-configuration/target/abixen-platform-configuration.jar &

# To check if eureka and configuration service is up, since other service are dependent on this service.
timestamp=0
flag=0
while ([ $timestamp -lt 60 ]  && [ $flag -eq 0 ])
do
   echo $timestamp 
   lsof -n -i4TCP:8761 | grep LISTEN && lsof -n -i4TCP:8888 | grep LISTEN && flag=1
   timestamp=`expr $timestamp + 10`
   sleep 10
done
echo $flag

echo "Starting abixen-platform-hystrix-dashboard and abixen-platform-gateway"
echo "Hope you have redis server running else 'abixen-platform-gateway' microservice won't work."

nohup java -jar abixen-platform-hystrix-dashboard/target/abixen-platform-hystrix-dashboard.jar &
nohup java -jar abixen-platform-gateway/target/abixen-platform-gateway.jar &

# To check if gateway service is up, since other service are dependent on gateway service.
timestamp=0
flag=0
while ([ $timestamp -lt 120 ]  && [ $flag -eq 0 ])
do
   echo $timestamp 
   lsof -n -i4TCP:8989 | grep LISTEN && lsof -n -i4TCP:9090 | grep LISTEN && flag=1
   timestamp=`expr $timestamp + 10`
   sleep 10
done

echo "Starting abixen-platform-core, abixen-platform-business-intelligence-service, abixen-platform-web-content-service, abixen-platform-web-client & abixen-platform-zipkin"

nohup java -jar -Dspring.profiles.active=dev abixen-platform-web-client/target/abixen-platform.war &
nohup java -jar -Dspring.profiles.active=dev abixen-platform-core/target/abixen-platform-core.jar &
nohup java -jar -Dspring.profiles.active=dev abixen-platform-business-intelligence-service/target/abixen-platform-business-intelligence-service.jar &
nohup java -jar -Dspring.profiles.active=dev abixen-platform-web-content-service/target/abixen-platform-web-content-service.jar &
nohup java -jar -Dspring.profiles.active=dev abixen-platform-zipkin/target/abixen-platform-zipkin.jar &

echo "-------------------------------------------"
echo "For logs, run 'tail -f nohup.out'"
echo "-------------------------------------------"

