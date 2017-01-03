#!/bin/sh
set -e

if [ "$TRAVIS_BRANCH" = "master" ] && [ "$TRAVIS_PULL_REQUEST" = "false" ]; then
	echo "Starting analysis by SonarQube..."
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -DskipDocker -B -e -V \
		-Dsonar.host.url=https://sonarqube.com \
		-Dsonar.login=$SONAR_TOKEN

elif [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ -n "${GITHUB_TOKEN-}" ]; then
	echo "Starting Pull Request analysis by SonarQube..."
	mvn clean install sonar:sonar -DskipDocker \
	    -Dsonar.host.url=https://sonarqube.com \
	    -Dsonar.login=$SONAR_TOKEN \
	    -Dsonar.analysis.mode=preview \
		-Dsonar.github.oauth=$GITHUB_TOKEN \
		-Dsonar.github.repository=$TRAVIS_REPO_SLUG \
        -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST
else
	echo "Starting build without SonarQube..."
	mvn clean install -DskipDocker
fi