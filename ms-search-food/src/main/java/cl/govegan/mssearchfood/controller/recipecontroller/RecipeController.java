package cl.govegan.mssearchfood.controller.recipecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.govegan.mssearchfood.models.recipe.Recipe;
import cl.govegan.mssearchfood.services.recipeservices.RecipeService;
import cl.govegan.mssearchfood.utils.responses.ResponseHttp;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping()
    public ResponseEntity<ResponseHttp<Page<Recipe>>> findAllRecipes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipesResult = recipeService.findAll(pageable);

        if (recipesResult.hasContent()) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Recipes found", recipesResult));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/findBySearch")
    public ResponseEntity<ResponseHttp<Page<Recipe>>> searchRecipeByText(
            @RequestParam String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipesResult = recipeService.findByTitleContaining(search, pageable);

        if (recipesResult.hasContent()) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Recipes found", recipesResult));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<ResponseHttp<Recipe>> findRecipeById(@RequestParam String recipeId) {
        Recipe recipe = recipeService.findById(recipeId);

        if (recipe != null) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Recipe found", recipe));
        } else {
            return ResponseEntity.noContent().build();
        }

        
    }
}