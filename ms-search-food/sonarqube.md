# Runing DEV profile
```cmd
mvn clean spring-boot:run -Pdev  
```

# SonarQube
## Verify

```cmd
mvn -D"sonar.projectKey=ms-search-food" -D"sonar.projectName=ms-search-food" -D"sonar.login=sqp_6b1852069e08d1889523d5d3f0f72249085e0bca" -D"sonar.java.libraries.exclusions='**/logback-classic-*.jar,**/logback-core-*.jar'" clean verify sonar:sonar -Pdev
```

## Install
```
mvn -D"sonar.projectKey=ms-search-food" -D"sonar.projectName=ms-search-food" -D"sonar.login=sqp_6b1852069e08d1889523d5d3f0f72249085e0bca" -D"sonar.java.libraries.exclusions='**/logback-classic-*.jar,**/logback-core-*.jar'" clean install sonar:sonar -Pdev
```