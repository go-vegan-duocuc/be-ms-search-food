package cl.govegan.mssearchfood.web.request.reciperequest.reciperequestfields;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeData {
   private String difficulty;
   private String yield;
   private String preparationTime;
   private String cookingTime;
}
