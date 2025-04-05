FROM common-service:latest
LABEL Name=shop-service Version=1.0.0 

# Container configuration
RUN mkdir -p /app/shop-service
COPY . /app/shop-service
WORKDIR /app/shop-service
RUN mvn clean package -DskipTests=true 
EXPOSE 8085
CMD ["java", "-jar", "target/shop-service-0.0.1-SNAPSHOT.jar"]