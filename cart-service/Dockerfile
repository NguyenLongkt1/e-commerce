FROM common-service:latest as builder
WORKDIR /app/cart-service
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/cart-service/target/cart-service-0.0.1-SNAPSHOT.jar cart-service.jar

EXPOSE 8086
CMD ["java", "-jar", "cart-service.jar"]
