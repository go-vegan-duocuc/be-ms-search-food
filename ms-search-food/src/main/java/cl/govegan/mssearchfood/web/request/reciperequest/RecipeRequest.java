package cl.govegan.mssearchfood.web.request.reciperequest;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import cl.govegan.mssearchfood.web.request.reciperequest.reciperequestfields.NutritionalInfo;
import cl.govegan.mssearchfood.web.request.reciperequest.reciperequestfields.RecipeData;
import cl.govegan.mssearchfood.web.request.reciperequest.reciperequestfields.YieldPerAge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recipes")
public class RecipeRequest {
      @Id private String id;
      private String title;
      private String uri;
      private String image;
      private RecipeData recipeData;
      private String[] specialNeeds;
      private NutritionalInfo nutritionalInfo;
      private YieldPerAge yieldPerAge;
      private String[] ingredientLines;
      private String[] directions;
      private String[] tags;
}
