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

import cl.govegan.mssearchfood.controller.foodcontroller.FoodController;
import cl.govegan.mssearchfood.models.food.Food;

@Component
public class FoodResourceAssembler extends RepresentationModelAssemblerSupport<Food, FoodResource> {

    public FoodResourceAssembler() {
        super(FoodController.class, FoodResource.class);
    }

    @Override
    public @NonNull FoodResource toModel(@NonNull Food food) {
        FoodResource resource = new FoodResource();
        resource.setId(food.getId());
        resource.setName(food.getName());
        resource.setWaterPercentage(food.getWaterPercentage());
        resource.setCaloriesKcal(food.getCaloriesKcal());
        resource.setProteinG(food.getProteinG());
        resource.setTotalFatG(food.getTotalFatG());
        resource.setCarbohydratesG(food.getCarbohydratesG());
        resource.setDietaryFiberG(food.getDietaryFiberG());
        resource.setCalciumMg(food.getCalciumMg());
        resource.setPhosphorusMg(food.getPhosphorusMg());
        resource.setIronMg(food.getIronMg());
        resource.setThiamineMg(food.getThiamineMg());
        resource.setRiboflavinMg(food.getRiboflavinMg());
        resource.setNiacinMg(food.getNiacinMg());
        resource.setVitaminCmg(food.getVitaminCmg());
        resource.setVitaminAEquivRetinolMcg(food.getVitaminAEquivRetinolMcg());
        resource.setMonounsaturatedFatG(food.getMonounsaturatedFatG());
        resource.setPolyunsaturatedFatG(food.getPolyunsaturatedFatG());
        resource.setSaturatedFatG(food.getSaturatedFatG());
        resource.setCholesterolMg(food.getCholesterolMg());
        resource.setPotassiumMg(food.getPotassiumMg());
        resource.setSodiumMg(food.getSodiumMg());
        resource.setZincMg(food.getZincMg());
        resource.setMagnesiumMg(food.getMagnesiumMg());
        resource.setVitaminB6Mg(food.getVitaminB6Mg());
        resource.setVitaminB12Mcg(food.getVitaminB12Mcg());
        resource.setFolicAcidMcg(food.getFolicAcidMcg());
        resource.setFolateEquivFDMcg(food.getFolateEquivFDMcg());
        resource.setEdibleFractionPercentage(food.getEdibleFractionPercentage());
        resource.setCategoryId(food.getCategoryId());
        resource.setCategoryName(food.getCategoryName());

        // Añadir enlaces HATEOAS
        resource.add(linkTo(methodOn(FoodController.class).findFoodById(food.getId())).withSelfRel());
        resource.add(linkTo(methodOn(FoodController.class).findAllFoods(0, 10)).withRel("foods"));
        
        return resource;
    }

    public PagedModel<FoodResource> toPagedModel(Page<Food> page) {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
            page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages()
        );
        List<FoodResource> resources = page.getContent().stream()
            .map(this::toModel)
            .collect(Collectors.toList());
        PagedModel<FoodResource> pagedModel = PagedModel.of(resources, metadata);
        pagedModel.add(linkTo(methodOn(FoodController.class).findAllFoods(page.getNumber(), page.getSize())).withSelfRel());
        return pagedModel;
    }
}