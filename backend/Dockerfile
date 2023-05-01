FROM openjdk:19-jdk-slim as builder

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests

FROM openjdk:19-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 3111

ENTRYPOINT ["java", "-jar", "app.jar"]
