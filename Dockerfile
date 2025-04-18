# Stage 1: Build the app using Maven
FROM maven:3.8.6-openjdk-17-slim AS builder

# Set work directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Run the app
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (Render assigns it via PORT env var)
EXPOSE 8080

# Run the application
CMD ["sh", "-c", "java -jar app.jar"]
