FROM openjdk:8u212-jre-alpine3.9

#Run apk add curl jq
RUN apk --no-cache add curl jq
# Workspace
WORKDIR /usr/share/udemy

# ADD .jar under target from host
# into this image
ADD target/selenium-docker.jar 			selenium-docker.jar
ADD target/selenium-docker-tests.jar 	selenium-docker-tests.jar
ADD target/libs                         libs

# ADD suite files
ADD book-flight-module.xml				book-flight-module.xml
ADD search-module.xml	                search-module.xml

# Add Health Check
Add healthcheck.sh                      healthcheck.sh

# BROWSER , HUB_HOST , MODULE

# Previous Entry point
#ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DBROWSER=$BROWSER -DHUB_HOST=$HUB_HOST org.testng.TestNG $MODULE

# Updated EntryPoint
ENTRYPOINT sh healthcheck.sh