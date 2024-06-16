package cl.govegan.mssearchfood.service;

import org.springframework.stereotype.Service;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretName;

@Service
public class SecretManagerService {

   public String accessSecret(String projectId, String secretId, String versionId) {
      try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
          SecretName secretName = SecretName.of(projectId, secretId);
          AccessSecretVersionResponse response = client.accessSecretVersion(secretName.toString() + "/versions/" + versionId);
          return response.getPayload().getData().toStringUtf8();
      } catch (Exception e) {
          throw new RuntimeException("Failed to access secret", e);
      }
  }
   
}
