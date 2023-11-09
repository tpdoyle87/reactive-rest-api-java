# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Copy source code to the container
COPY src /home/app/src

# Copy the pom.xml file
COPY pom.xml /home/app

# Package the application
RUN mvn -f /home/app/pom.xml clean package

# Stage 2: Create the Docker image containing only the compiled jar
FROM openjdk:17-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Copy the jar file from the build stage to the /app directory
COPY --from=build /home/app/target/*.jar /app/app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
