FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY shared/contracts/pom.xml shared/contracts/pom.xml
COPY shared/contracts/src shared/contracts/src
RUN mvn -q -f shared/contracts/pom.xml install -DskipTests
COPY backend/integration-service/pom.xml backend/integration-service/pom.xml
COPY backend/integration-service/src backend/integration-service/src
RUN mvn -q -f backend/integration-service/pom.xml package -DskipTests
FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y --no-install-recommends wget && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /workspace/backend/integration-service/target/integration-service-0.1.0.jar app.jar
EXPOSE 8094
ENTRYPOINT ["java","-jar","/app/app.jar"]
