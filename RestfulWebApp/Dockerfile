FROM gradle:8.14.3-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:17-jre

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 80

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://mongodb:27017/seminar1", "-Dserver.port=80", "-jar", "app.jar"]