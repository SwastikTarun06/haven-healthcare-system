# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy everything from your repo into the /app folder
COPY . .

# This command helps us debug. It will list the files so we can see where the pom.xml is.
RUN ls -R

# Run the build. We use -f to force it to find the pom.xml if it's in a sub-folder.
RUN mvn clean package -DskipTests
