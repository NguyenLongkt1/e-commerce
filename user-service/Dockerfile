FROM common-service:latest
LABEL Name=user-service Version=1.0.0 

# Container configuration
RUN mkdir -p /app/user-service
COPY . /app/user-service
WORKDIR /app/user-service
RUN mvn clean package -DskipTests=true 
EXPOSE 8081
CMD ["java", "-jar", "target/user-service-0.0.1-SNAPSHOT.jar"]