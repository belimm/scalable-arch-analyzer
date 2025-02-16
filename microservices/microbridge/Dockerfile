# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from target directory (ensure you have built the project)
COPY target/microbridge-0.0.1-SNAPSHOT.jar app.jar

# Expose port (matches server.port in application.properties)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]