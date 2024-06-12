# Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS buildstage

WORKDIR /ms-search-food

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Use a slim JDK image for the runtime
FROM eclipse-temurin:21 AS runstage

WORKDIR /app

# Crear el directorio de logs
RUN mkdir /app/logs

COPY --from=buildstage /ms-search-food/target/ms-search-food-0.0.1-SNAPSHOT.jar ms-search-food.jar

ENTRYPOINT ["java", "-Dlogging.file.path=/app/logs", "-jar", "ms-search-food.jar"]
