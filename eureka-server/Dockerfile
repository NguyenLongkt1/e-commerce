FROM common-service:latest
LABEL Name=eureka-server Version=1.0.0 

# Container configuration
RUN mkdir -p /app/eureka-server
COPY . /app/eureka-server
WORKDIR /app/eureka-server
RUN mvn clean package -DskipTests=true 
EXPOSE 8761
CMD ["java", "-jar", "target/eureka-server-0.0.1-SNAPSHOT.jar"]