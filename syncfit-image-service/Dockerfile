FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} image-service.jar
ENTRYPOINT ["java", "-jar", "/image-service.jar"]