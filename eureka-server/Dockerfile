FROM common-service:latest as builder
WORKDIR /app/eureka-server
COPY . .
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

EXPOSE 8761
CMD ["java", "-jar", "eureka-server.jar"]
