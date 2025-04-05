FROM common-service:latest
LABEL Name=category-service Version=1.0.0 

# Container configuration
RUN mkdir -p /app/category-service
COPY . /app/category-service
WORKDIR /app/category-service
RUN mvn clean package -DskipTests=true 
EXPOSE 8082
CMD ["java", "-jar", "target/category-service-0.0.1-SNAPSHOT.jar"]