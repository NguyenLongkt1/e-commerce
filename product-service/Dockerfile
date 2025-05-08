FROM common-service:latest as builder
WORKDIR /app/product-service
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/product-service/target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

EXPOSE 8083
CMD ["java", "-jar", "product-service.jar"]
