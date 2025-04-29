FROM common-service:latest
LABEL Name=cart-service Version=1.0.0

# Container configuration
RUN mkdir -p /app/cart-service
COPY . /app/cart-service
WORKDIR /app/cart-service
RUN mvn clean package -DskipTests=true 
EXPOSE 8086
CMD ["java", "-jar", "target/cart-service-0.0.1-SNAPSHOT.jar"]
