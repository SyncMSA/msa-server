FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} externalApi-service.jar
ENTRYPOINT ["java", "-jar", "/externalApi-service.jar"]