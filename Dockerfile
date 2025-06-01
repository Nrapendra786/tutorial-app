#FROM openjdk:17
#
## Copy the JAR package into the image
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#
## Expose the application port
#EXPOSE 8090
#
## Run the App
#ENTRYPOINT ["java", "-jar", "/app.jar"]


FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/*.jar ./app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]





