# Use the OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Copy the application JAR to the container
COPY ${JAR_FILE} app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]