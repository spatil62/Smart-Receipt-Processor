# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code to the working directory
COPY pom.xml .
COPY src ./src

# Run Maven clean and install to build the project and run tests
RUN mvn clean install

# Stage 2: Create a minimal runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/target/receipt_processor_new-0.0.1-SNAPSHOT.jar /app/receipt_processor_new.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/receipt_processor_new.jar"]

# Expose the port the application will run on
EXPOSE 8080

