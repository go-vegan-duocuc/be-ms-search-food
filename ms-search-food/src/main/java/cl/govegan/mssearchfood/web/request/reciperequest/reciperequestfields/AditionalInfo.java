package cl.govegan.mssearchfood.web.request.reciperequest.reciperequestfields;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AditionalInfo {
      private String sugar;
      private String fiber;
      private String saturatedFat;
      private String salt;   
}
