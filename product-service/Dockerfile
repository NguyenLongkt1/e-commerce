FROM common-service:latest
LABEL Name=product-service Version=1.0.0 

# Container configuration
RUN mkdir -p /app/product-service
COPY . /app/product-service
WORKDIR /app/product-service
RUN mvn clean package -DskipTests=true 
EXPOSE 8083
CMD ["java", "-jar", "target/product-service-0.0.1-SNAPSHOT.jar"]