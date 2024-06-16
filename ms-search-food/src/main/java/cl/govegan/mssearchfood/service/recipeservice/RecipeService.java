package cl.govegan.mssearchfood.service.recipeservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.govegan.mssearchfood.model.recipe.Recipe;

@Service
public interface RecipeService {
   
   Page<Recipe> findAll(Pageable pageable);
   Page<Recipe> findByTitleContaining(String keywords, Pageable pageable);
   Recipe findById(String id);

}
