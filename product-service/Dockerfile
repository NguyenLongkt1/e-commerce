ARG BASE_IMAGE=common-service:latest
FROM ${BASE_IMAGE}
LABEL Name=product-service Version=1.0.0 

# Container configuration
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests=true 
RUN ls -l target/
EXPOSE 8083
CMD ["java", "-jar", "target/product-service-0.0.1-SNAPSHOT.jar"] 
