FROM openjdk:11-jre
ARG JAR_FILE=build/libs/*.jar
WORKDIR /app
COPY ${JAR_FILE} /app/dhs-server.jar
ENTRYPOINT ["java", "-jar", "dhs-server.jar"]
