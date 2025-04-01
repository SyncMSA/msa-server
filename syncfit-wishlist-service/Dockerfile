FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} wishlist-service.jar
ENTRYPOINT ["java", "-jar", "/wishlist-service.jar"]