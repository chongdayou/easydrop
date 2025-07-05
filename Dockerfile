FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY wait-for-postgres.sh ./wait-for-postgres.sh
RUN apt-get update && apt-get install -y netcat-openbsd && chmod +x ./wait-for-postgres.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-postgres.sh", "easydrop-db", "java", "-jar", "app.jar"]