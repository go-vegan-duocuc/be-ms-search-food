package cl.govegan.mssearchfood.controller.foodcontroller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.govegan.mssearchfood.HATEOAS.FoodCategoryAssembler;
import cl.govegan.mssearchfood.HATEOAS.FoodCategoryResource;
import cl.govegan.mssearchfood.HATEOAS.FoodResource;
import cl.govegan.mssearchfood.HATEOAS.FoodResourceAssembler;
import cl.govegan.mssearchfood.models.food.Food;
import cl.govegan.mssearchfood.models.food.FoodCategory;
import cl.govegan.mssearchfood.services.foodservices.FoodService;
import cl.govegan.mssearchfood.utils.requests.food.FoodRequest;
import cl.govegan.mssearchfood.utils.responses.ResponseHttp;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodResourceAssembler assembler;

    @Autowired
    private FoodCategoryAssembler categoryAssembler;

    @GetMapping()
    public ResponseEntity<PagedModel<FoodResource>> findAllFoods(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foodsResult = foodService.findAll(pageable);

        // logger
        logger.debug("Foods found: " + foodsResult.getTotalElements());

        if (foodsResult.hasContent()) {
            PagedModel<FoodResource> pagedModel = assembler.toPagedModel(foodsResult);
            return ResponseEntity.ok(pagedModel);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/findBySearch")
    public ResponseEntity<PagedModel<FoodResource>> searchFoodByText(
            @RequestParam String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foodsResult = foodService.findByFoodNameContaining(search, pageable);

        if (foodsResult.hasContent()) {
            PagedModel<FoodResource> pagedModel = assembler.toPagedModel(foodsResult);
            return ResponseEntity.ok(pagedModel);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<FoodResource> findFoodById(@RequestParam String id) {
        Optional<Food> food = foodService.findById(id);

        if (food.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(assembler.toModel(food.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseHttp<Food>> saveFood(@RequestBody FoodRequest foodRequest) {
        Food savedFood = foodService.saveFood(foodRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseHttp<>(201, "Food saved", savedFood));
    }

    @DeleteMapping()
    public ResponseEntity<ResponseHttp<String>> deleteFood(@RequestParam String id) {
        foodService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseHttp<>(200, "Food deleted successfully", null));
    }

    @GetMapping("/categories")
    public ResponseEntity<CollectionModel<FoodCategoryResource>> findAllCategories() {
        
        List<FoodCategory> categories = foodService.findAllCategories();
        logger.debug("Categories found: " + categories.size());

        if (!categories.isEmpty()) {
            CollectionModel<FoodCategoryResource> collectionModel = categoryAssembler.toCollectionModel(categories);
            return ResponseEntity.ok(collectionModel);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

}