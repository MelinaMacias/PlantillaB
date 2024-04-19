FROM amazoncorretto:17-alpine-jdk
LABEL authors="mmacias"

WORKDIR /app
COPY target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]