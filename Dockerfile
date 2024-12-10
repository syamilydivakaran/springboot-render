# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Copy the pom.xml and download the dependencies (if you need this step, uncomment)
COPY pom.xml /app/
#RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Stage 2: Prepare the runtime environment
FROM openjdk:17.0.1-jdk-slim AS runtime

# Create the necessary directory
RUN mkdir -p /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/bookmanagement-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port for the app
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
