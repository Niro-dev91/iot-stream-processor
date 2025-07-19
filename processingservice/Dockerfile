# Use official OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Install netcat for the wait script
RUN apk add --no-cache netcat-openbsd

# Set working directory inside container
WORKDIR /app

# Copy the built jar file
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
