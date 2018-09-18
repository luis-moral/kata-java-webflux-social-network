FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8001/tcp

ARG JAR_FILE=build/libs/social-network-rest-1.0.0-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]