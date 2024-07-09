#Dockerfile
FROM maven:3.9.6-eclipse-temurin-17 AS buildstage

WORKDIR /ms-search-food

COPY pom.xml .
COPY src ./src

RUN mvn clean package

RUN mvn -D"sonar.projectKey=ms-search-food-branch" -D"sonar.projectName=ms-search-food-branch" -D"sonar.login=sqa_5343de562a0b0b16289a0c81b75d70a2202a2ae5" verify sonar:sonar

FROM eclipse-temurin:17 AS runstage

WORKDIR /app

COPY --from=buildstage /ms-search-food/target/ms-search-food-0.0.1-SNAPSHOT.jar ms-search-food.jar

ENTRYPOINT ["java", "-jar", "ms-search-food.jar"]