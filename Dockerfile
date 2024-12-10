# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
#WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY . .
#RUN mvn dependency:go-offline

# Copy the source code and build the application
#COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Prepare the runtime environment
FROM openjdk:17.0.1-jdk-slim AS runtime
#WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/bookmanagement-0.0.1-SNAPSHOT.jar ./app.jar

# Copy the .env file for environment variables
#COPY .env /app/.env

# Expose the port for the app
EXPOSE 8081

# Set the environment variable for loading the .env file (if needed by your application)
#ENV DOTENV_FILE=/app/.env

# Command to run the application
ENTRYPOINT ["java", "-jar", "bookmanagement.jar"]
