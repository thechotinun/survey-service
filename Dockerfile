FROM maven:3.8-openjdk-17 AS builder

WORKDIR /app

# Copy the project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Create runtime image
FROM openjdk:17-slim

WORKDIR /app

# Copy the JAR file
COPY --from=builder /app/target/survey-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 3100

# Run the application
CMD ["java", "-Dserver.port=3100", "-jar", "app.jar"]