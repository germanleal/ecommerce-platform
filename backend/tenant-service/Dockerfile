FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY shared/contracts /workspace/shared/contracts
RUN mvn -q -f /workspace/shared/contracts/pom.xml install -DskipTests
COPY shared/security /workspace/shared/security
RUN mvn -q -f /workspace/shared/security/pom.xml install -DskipTests
COPY backend/tenant-service/pom.xml /workspace/backend/tenant-service/pom.xml
COPY backend/tenant-service/src /workspace/backend/tenant-service/src
WORKDIR /workspace/backend/tenant-service
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/backend/tenant-service/target/tenant-service-0.1.0.jar app.jar
EXPOSE 8098
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
