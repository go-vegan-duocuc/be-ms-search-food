package cl.govegan.mssearchfood.model.recipe;

import cl.govegan.mssearchfood.model.recipe.recipefields.NutritionalInfo;
import cl.govegan.mssearchfood.model.recipe.recipefields.RecipeData;
import cl.govegan.mssearchfood.model.recipe.recipefields.YieldPerAge;
import cl.govegan.mssearchfood.utils.jsonutils.IgnoreDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recipes")
@Builder
public class Recipe {
    @Id
    private String id;
    private String title;
    private String uri;
    private String image;
    private RecipeData recipeData;
    private String[] specialNeeds;
    private NutritionalInfo nutritionalInfo;
    private YieldPerAge yieldPerAge;
    private String[] ingredientLines;
    private String[] directions;
    private String chefTips;
    private String[] tags;

    @JsonDeserialize(using = IgnoreDeserializer.class)
    private String titleNormalized;
}
