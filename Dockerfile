FROM eclipse-temurin:17 AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew \
    && ./gradlew clean bootJar -x test

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=cloudconfig"]