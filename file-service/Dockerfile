FROM common-service:latest
LABEL Name=file-service Version=1.0.0 

# Container configuration
RUN mkdir -p /app/file-service
COPY . /app/file-service
WORKDIR /app/file-service
RUN mvn clean package -DskipTests=true 
EXPOSE 8084
CMD ["java", "-jar", "target/file-service-0.0.1-SNAPSHOT.jar"]