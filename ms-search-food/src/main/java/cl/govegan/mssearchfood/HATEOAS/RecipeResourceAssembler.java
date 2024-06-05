package cl.govegan.mssearchfood.HATEOAS;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import cl.govegan.mssearchfood.controller.recipecontroller.RecipeController;
import cl.govegan.mssearchfood.models.recipe.Recipe;

@Component
public class RecipeResourceAssembler extends RepresentationModelAssemblerSupport<Recipe, RecipeResource> {

    public RecipeResourceAssembler() {
        super(RecipeController.class, RecipeResource.class);
    }

    @Override
    public @NonNull RecipeResource toModel(@NonNull Recipe recipe) {
        RecipeResource resource = new RecipeResource();
        resource.setId(recipe.getId());
        resource.setTitle(recipe.getTitle());
        resource.setUri(recipe.getUri());
        resource.setImage(recipe.getImage());
        resource.setRecipeData(recipe.getRecipeData());
        resource.setSpecialNeeds(recipe.getSpecialNeeds());
        resource.setNutritionalInfo(recipe.getNutritionalInfo());
        resource.setYieldPerAge(recipe.getYieldPerAge());
        resource.setIngredientLines(recipe.getIngredientLines());
        resource.setDirections(recipe.getDirections());
        resource.setChefTips(recipe.getChefTips());
        resource.setTags(recipe.getTags());
        
        // Añadir enlaces HATEOAS
        resource.add(linkTo(methodOn(RecipeController.class).findRecipeById(recipe.getId())).withSelfRel());
        resource.add(linkTo(methodOn(RecipeController.class).findAllRecipes(0, 10)).withRel("recipes"));
        
        return resource;
    }

    public PagedModel<RecipeResource> toPagedModel(Page<Recipe> page) {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
            page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages()
        );
        List<RecipeResource> resources = page.getContent().stream()
            .map(this::toModel)
            .collect(Collectors.toList());
        PagedModel<RecipeResource> pagedModel = PagedModel.of(resources, metadata);
        pagedModel.add(linkTo(methodOn(RecipeController.class).findAllRecipes(page.getNumber(), page.getSize())).withSelfRel());
        return pagedModel;
    }
}