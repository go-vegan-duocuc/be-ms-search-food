# Running SonarQube Locally

Running the Sonarqube in a Docker container. Start the server by running this command:

```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

