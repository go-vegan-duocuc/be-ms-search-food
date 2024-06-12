package cl.govegan.mssearchfood.HATEOAS;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import cl.govegan.mssearchfood.controller.foodcontroller.FoodController;
import cl.govegan.mssearchfood.models.food.FoodCategory;

@Component
public class FoodCategoryAssembler extends RepresentationModelAssemblerSupport<FoodCategory, FoodCategoryResource>{

   public FoodCategoryAssembler() {
      super(FoodCategory.class, FoodCategoryResource.class);
   }

   @Override
   public @NonNull FoodCategoryResource toModel(@NonNull FoodCategory FoodCategory) {

      FoodCategoryResource resource = new FoodCategoryResource();
      resource.setId(FoodCategory.getId());
      resource.setCategoryName(FoodCategory.getCategoryName());
      resource.setIdCategory(FoodCategory.getIdCategory());

      resource.add(linkTo(methodOn(FoodController.class).findAllCategories()).withRel("foodCategories"));
      
      return resource;
   }

   public @NonNull CollectionModel<FoodCategoryResource> toCollectionModel(@NonNull List<FoodCategory> foodCategoriesList) {

      CollectionModel<FoodCategoryResource> foodCategoriesResources = super.toCollectionModel(foodCategoriesList);
      foodCategoriesResources.add(linkTo(methodOn(FoodController.class).findAllCategories()).withSelfRel());

      return foodCategoriesResources;
   }
   
}
