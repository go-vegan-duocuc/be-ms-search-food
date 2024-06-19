package cl.govegan.mssearchfood.service.recipeservice;

import cl.govegan.mssearchfood.model.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface RecipeService {

    Page<Recipe> findAllRecipes (Pageable pageable);

    Page<Recipe> findAllRecipesBySearch (String query, Pageable pageable);

    Optional<Recipe> findById (String id);

    Recipe saveRecipe (Map<String, Object> body);

    Recipe updateRecipe (Map<String, Object> body);

    void deleteById (String id);

    Page<Recipe> findAllRecipesBySpecialNeeds (String query, Pageable pageable);

}
