FROM openjdk:17-jdk-alpine
LABEL maintainer="alex.galvis.sistemas@gmail.com"

# Set the working directory inside the container
WORKDIR /app

# Copy any JAR file from the target directory to the container
COPY target/*.jar earthquake-svc.jar

# Expose the port your Spring Boot app listens on (change as needed)
EXPOSE 8092

# Command to run the application
ENTRYPOINT ["java", "-jar", "earthquake-svc.jar"]