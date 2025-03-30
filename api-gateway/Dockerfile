FROM common-service:latest
LABEL Name=api-gateway Version=1.0.0 

# Container configuration
RUN mkdir -p /app/api-gateway
COPY . /app/api-gateway
WORKDIR /app/api-gateway
RUN mvn clean package -DskipTests=true 
EXPOSE 8888
CMD ["java", "-jar", "target/api-gateway-0.0.1-SNAPSHOT.jar"]