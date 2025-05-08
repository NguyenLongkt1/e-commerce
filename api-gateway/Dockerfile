FROM common-service:latest as builder
WORKDIR /app/api-gateway
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

EXPOSE 8888
CMD ["java", "-jar", "api-gateway.jar"]
