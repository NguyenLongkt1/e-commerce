FROM common-service:latest as builder
WORKDIR /app/user-service
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/user-service/target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

EXPOSE 8081
CMD ["java", "-jar", "user-service.jar"]
