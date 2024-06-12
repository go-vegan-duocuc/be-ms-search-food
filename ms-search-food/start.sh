#!/bin/bash
# Obtener el secreto de Google Cloud Secret Manager
MONGO_URI=$(gcloud secrets versions access latest --secret=mongodb_uri_ms-search-food)
export MONGO_URI

# Ejecutar la aplicación
java -Dlogging.file.path=/app/logs -jar ms-search-food.jar