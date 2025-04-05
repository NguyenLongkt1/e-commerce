FROM maven:3.9-amazoncorretto-17
LABEL Name=common Version=1.0.0 

# Container configuration
RUN mkdir /common
COPY . /common
WORKDIR /common
VOLUME /common
RUN mvn clean install -DskipTests=true

