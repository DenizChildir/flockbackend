# Start with the official OpenJDK image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build context to the working directory
COPY target/my-springboot-app-*.jar my-springboot-app.jar

# Expose the application's port
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "my-springboot-app.jar"]
