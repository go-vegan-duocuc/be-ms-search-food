package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.recipe.Recipe;
import cl.govegan.mssearchfood.service.recipeservice.RecipeService;
import cl.govegan.mssearchfood.utils.message.ErrorMessage;
import cl.govegan.mssearchfood.utils.message.SuccessMessage;
import cl.govegan.mssearchfood.utils.page.Paginator;
import cl.govegan.mssearchfood.utils.request.Normalization;
import cl.govegan.mssearchfood.utils.response.ResponseBuilder;
import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import cl.govegan.mssearchfood.web.response.ApiPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus () {
        return ResponseEntity.ok("OK");
    }

    @GetMapping()
    public ResponseEntity<ApiPageResponse<Recipe>> getAllRecipes (
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Recipe> recipesResult = recipeService.findAllRecipes(Paginator.getPageable(page, size));

        return ResponseBuilder.buildPageResponse((!recipesResult.isEmpty()) ?
                        SuccessMessage.DATA_FOUND
                        : ErrorMessage.DATA_NOT_FOUND,
                recipesResult);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiPageResponse<Recipe>> getAllFoodsBySearch (
            @RequestParam String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Recipe> recipesResult = recipeService.findAllRecipesBySearch(
                Normalization.normalizeText(query), Paginator.getPageable(page, size));

        return ResponseBuilder.buildPageResponse((!recipesResult.isEmpty()) ?
                        SuccessMessage.DATA_FOUND_BY_SEARCH + query
                        : ErrorMessage.DATA_NOT_FOUND,
                recipesResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<Recipe>> getRecipeById (@PathVariable String id) {

        Recipe recipe = recipeService.findById(id).orElse(null);

        return ResponseBuilder.buildEntityResponse((recipe != null) ?
                        SuccessMessage.DATA_FOUND_BY_ID + id
                        : ErrorMessage.DATA_NOT_FOUND_BY_ID,
                recipe);
    }

    @PostMapping ("/special-needs")
    public ResponseEntity<ApiPageResponse<Recipe>> getRecipesBySpecialNeed (
            @RequestBody Map<String, String> body,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        String specialNeed = body.get("specialNeed");
        Page<Recipe> recipesResult = recipeService.findAllRecipesBySpecialNeeds(specialNeed, Paginator.getPageable(page, size));

        return ResponseBuilder.buildPageResponse((!recipesResult.isEmpty()) ?
                        SuccessMessage.DATA_FOUND_BY_SEARCH + specialNeed
                        : ErrorMessage.DATA_NOT_FOUND_BY_SEARCH + specialNeed,
                recipesResult);
    }

    @PostMapping()
    public ResponseEntity<ApiEntityResponse<Recipe>> saveRecipe (@RequestBody Map<String, Object> body) {

        Recipe savedRecipe = recipeService.saveRecipe(body);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_SAVED, savedRecipe);
    }

    @PatchMapping()
    public ResponseEntity<ApiEntityResponse<Recipe>> updateRecipe (@RequestBody Map<String, Object> body) {

        Recipe updatedRecipe = recipeService.updateRecipe(body);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_UPDATED, updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<String>> deleteRecipe (@PathVariable String id) {

        recipeService.deleteById(id);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_DELETED, id);
    }

}