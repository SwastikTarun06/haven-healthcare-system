# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .

# We tell Maven to look inside that ghost folder for the pom.xml
RUN mvn -f haven-healthcare-system/pom.xml clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# We copy the jar from the ghost folder's target directory
COPY --from=build /app/haven-healthcare-system/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
