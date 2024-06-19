package cl.govegan.mssearchfood.service.recipeservice;

import cl.govegan.mssearchfood.exception.DatabaseOperationException;
import cl.govegan.mssearchfood.exception.InvalidRecipeDataException;
import cl.govegan.mssearchfood.model.recipe.Recipe;
import cl.govegan.mssearchfood.repository.RecipeRepository;
import cl.govegan.mssearchfood.utils.request.Normalization;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    //CONSTANTS
    private static final String TITLE = "title";
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Page<Recipe> findAllRecipes (Pageable pageable) {
        try {
            return recipeRepository.findAll(pageable);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching recipes");
        }
    }

    @Override
    public Page<Recipe> findAllRecipesBySearch (String query, Pageable pageable) {

        try {
            return recipeRepository.findByTitleNormalizedContaining(query, pageable);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching recipes by search query: " + query);
        }
    }

    @Override
    public Optional<Recipe> findById (String id) {
        try {
            return recipeRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching recipe by ID: " + id);
        }
    }

    @Override
    public Recipe saveRecipe (Map<String, Object> body) {
        if (body.get(TITLE) == null || body.get(TITLE).toString().isBlank()) {
            throw new InvalidRecipeDataException("Title is required");
        }

        Recipe recipe = objectMapper.convertValue(body, Recipe.class);
        recipe.setTitleNormalized(Normalization.normalizeText(recipe.getTitle()));

        try {
            return recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while saving recipe");
        }
    }

    @Override
    public Recipe updateRecipe (Map<String, Object> body) {
        if (body.get(TITLE) == null || body.get(TITLE).toString().isBlank()
                || body.get("id") == null || body.get("id").toString().isBlank()) {
            throw new InvalidRecipeDataException("Title and id are required");
        }

        String id = body.get("id").toString();
        Optional<Recipe> existingRecipeOpt = recipeRepository.findById(id);

        if (existingRecipeOpt.isEmpty()) {
            throw new InvalidRecipeDataException("Recipe with the given ID does not exist");
        }

        Recipe existingRecipe = existingRecipeOpt.get();
        Recipe updatedRecipe = objectMapper.convertValue(body, Recipe.class);

        BeanUtils.copyProperties(updatedRecipe, existingRecipe, "id");
        existingRecipe.setTitleNormalized(Normalization.normalizeText(updatedRecipe.getTitle()));

        try {
            return recipeRepository.save(existingRecipe);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while updating recipe");
        }
    }

    @Override
    public void deleteById (String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidRecipeDataException("Id is required");
        }

        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if (existingRecipe.isEmpty()) {
            throw new InvalidRecipeDataException("Recipe with the given ID does not exist");
        }

        try {
            recipeRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while deleting recipe");
        }

    }

    @Override
    public Page<Recipe> findAllRecipesBySpecialNeeds (String query, Pageable pageable) {
        try {
            return recipeRepository.findBySpecialNeedsContaining(query, pageable);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching recipes by special needs query: " + query);
        }
    }
}
