FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/AmanAlert-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "AmanAlert-0.0.1-SNAPSHOT.jar"]