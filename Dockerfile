FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
RUN mkdir -p /var/www/app
# ENTRYPOINT ["ping", "10.0.75.1"]
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app.jar"]
