FROM maven:3.9.7-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin
COPY --from=build /target/todolist-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-XX:+EnableDynamicAgentLoading", "-jar", "app.jar" ]