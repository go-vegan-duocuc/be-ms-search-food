package cl.govegan.mssearchfood.controller.recipecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.govegan.mssearchfood.HATEOAS.RecipeResource;
import cl.govegan.mssearchfood.HATEOAS.RecipeResourceAssembler;
import cl.govegan.mssearchfood.exceptions.ResourceNotFoundException;
import cl.govegan.mssearchfood.models.recipe.Recipe;
import cl.govegan.mssearchfood.services.recipeservices.RecipeService;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeResourceAssembler assembler;

    @GetMapping()
    public ResponseEntity<PagedModel<RecipeResource>> findAllRecipes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipesResult = recipeService.findAll(pageable);

        if (recipesResult.hasContent()) {
            PagedModel<RecipeResource> pagedModel = assembler.toPagedModel(recipesResult);
            return ResponseEntity.ok(pagedModel);
        } else {
            throw new ResourceNotFoundException("No recipes found");
        }
    }

    @GetMapping("/findBySearch")
    public ResponseEntity<PagedModel<RecipeResource>> searchRecipeByText(
            @RequestParam String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipesResult = recipeService.findByTitleContaining(search, pageable);

        if (recipesResult.hasContent()) {
            PagedModel<RecipeResource> pagedModel = assembler.toPagedModel(recipesResult);
            return ResponseEntity.ok(pagedModel);
        } else {
            throw new ResourceNotFoundException("No recipes found");
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<EntityModel<RecipeResource>> findRecipeById(@RequestParam String recipeId) {
        Recipe recipe = recipeService.findById(recipeId);

        if (recipe != null) {
            return ResponseEntity.ok(EntityModel.of(assembler.toModel(recipe)));
        } else {
            throw new ResourceNotFoundException("No recipe found");
        }
    }
}