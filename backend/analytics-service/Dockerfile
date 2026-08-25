FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY shared/contracts /workspace/shared/contracts
RUN mvn -q -f /workspace/shared/contracts/pom.xml install -DskipTests
COPY backend/analytics-service/pom.xml /workspace/backend/analytics-service/pom.xml
COPY backend/analytics-service/src /workspace/backend/analytics-service/src
WORKDIR /workspace/backend/analytics-service
RUN mvn -q -DskipTests package
FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y --no-install-recommends wget && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /workspace/backend/analytics-service/target/analytics-service-0.1.0.jar app.jar
EXPOSE 8093
ENTRYPOINT ["java","-jar","/app/app.jar"]
