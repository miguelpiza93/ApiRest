# Utiliza una imagen base de Java 17
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/ApiRest-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
