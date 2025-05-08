FROM common-service:latest as builder
WORKDIR /app/category-service
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/category-service/target/category-service-0.0.1-SNAPSHOT.jar category-service.jar

EXPOSE 8082
CMD ["java", "-jar", "category-service.jar"]
