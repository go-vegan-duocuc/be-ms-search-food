package cl.govegan.mssearchfood.HATEOAS;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import cl.govegan.mssearchfood.models.recipe.recipefields.NutritionalInfo;
import cl.govegan.mssearchfood.models.recipe.recipefields.RecipeData;
import cl.govegan.mssearchfood.models.recipe.recipefields.YieldPerAge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "recipes")
@EqualsAndHashCode(callSuper=false)
public class RecipeResource extends RepresentationModel<RecipeResource> {
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
}