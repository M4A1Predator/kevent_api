FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} kevent_api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kevent_api.jar"]