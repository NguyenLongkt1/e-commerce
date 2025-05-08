FROM common-service:latest as builder
WORKDIR /app/shop-service
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/shop-service/target/shop-service-0.0.1-SNAPSHOT.jar shop-service.jar

EXPOSE 8085
CMD ["java", "-jar", "shop-service.jar"]
