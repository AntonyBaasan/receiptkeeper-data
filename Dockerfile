FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG restdata-0.0.2-SNAPSHOT.jar
COPY target/restdata-0.0.2-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/app.jar"]