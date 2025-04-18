# Stage 1: Build the Spring Boot application
FROM openjdk:24-ea-17-slim-bookworm AS app

# Copy the Spring Boot application JAR
ADD ./target/survey-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application port
EXPOSE 3100

# Run the Spring Boot application
CMD ["java", "-Dserver.port=3100", "-jar", "/app/app.jar"]