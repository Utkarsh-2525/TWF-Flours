# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar into the container
COPY target/*.jar app.jar

# Expose port (Render uses the PORT environment variable)
EXPOSE 8080

# Set the command to run the jar file
CMD ["sh", "-c", "java -jar app.jar"]
