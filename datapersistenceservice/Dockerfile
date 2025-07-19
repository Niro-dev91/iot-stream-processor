# Use official OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Install netcat for the wait script
RUN apk add --no-cache netcat-openbsd

# Set working directory inside container
WORKDIR /app

# Copy the built jar file
COPY target/*.jar app.jar

# Copy the wait-for script
COPY wait-for-elasticsearch.sh /wait-for-elasticsearch.sh
RUN chmod +x /wait-for-elasticsearch.sh

# Expose port
EXPOSE 8080

# Use wait-for script as entrypoint
ENTRYPOINT ["/wait-for-elasticsearch.sh", "java", "-jar", "app.jar"]
