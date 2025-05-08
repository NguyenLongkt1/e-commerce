FROM common-service:latest as builder
WORKDIR /app/file-service
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/file-service/target/file-service-0.0.1-SNAPSHOT.jar file-service.jar

EXPOSE 8084
CMD ["java", "-jar", "file-service.jar"]
