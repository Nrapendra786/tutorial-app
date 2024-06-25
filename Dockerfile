# FROM maven:3.8.5-openjdk-17 # for Java 17
#
# Build stage
#
#FROM maven:3.8.3-openjdk-17 AS build
#LABEL name="spring boot webservice"
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/home/app/target/springboot-restful-webservices-0.0.1-SNAPSHOT.jar"]