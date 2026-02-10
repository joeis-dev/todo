# Base stage
FROM maven:3.9.6-eclipse-temurin-21 AS base
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

# Development stage
FROM base AS dev
COPY . .
CMD ["mvn", "spring-boot:run"]

# Build stage for production
FROM base AS build
COPY src ./src
RUN mvn package -DskipTests

# Production stage
FROM eclipse-temurin:21-jre-jammy AS prod
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
