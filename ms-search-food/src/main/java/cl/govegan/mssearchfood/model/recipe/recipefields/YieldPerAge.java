package cl.govegan.mssearchfood.model.recipe.recipefields;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YieldPerAge {
   private String adult;
   private String threeToEight;
   private String nineToTwelve;
   private String teen;   
}
