FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} member-service.jar
ENTRYPOINT ["java", "-jar", "/member-service.jar"]