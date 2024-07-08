FROM openjdk:17

# Copy the JAR package into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the application port
EXPOSE 8090

# Run the App
ENTRYPOINT ["java", "-jar", "/app.jar"]




#FROM bellsoft/liberica-openjdk-alpine:17
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.30.0/opentelemetry-javaagent.jar /opt/opentelemetry-agent.jar
#ENTRYPOINT java -javaagent:/opt/opentelemetry-agent.jar \
#    -Dotel.resource.attributes=service.instance.id=$HOSTNAME \
#    -jar /app.jar


