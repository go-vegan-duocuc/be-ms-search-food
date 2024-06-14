package cl.govegan.mssearchfood.controller.foodcontroller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping()
    public ResponseEntity<ResponseHttp<Page<Food>>> findAllFoods(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foodsResult = foodService.findAll(pageable);

        // logger
        logger.debug("Foods found: " + foodsResult.getTotalElements());

        if (foodsResult.hasContent()) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Foods found", foodsResult));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseHttp<>(204, "No content", null));
        }
    }

    @GetMapping("/findBySearch")
    public ResponseEntity<ResponseHttp<Page<Food>>> searchFoodByText(
            @RequestParam String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foodsResult = foodService.findByFoodNameContaining(search, pageable);

        // logger
        logger.debug("Foods found: " + foodsResult.getTotalElements());

        if (foodsResult.hasContent()) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Foods found", foodsResult));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseHttp<>(204, "No content", null));
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<ResponseHttp<Food>> findFoodById(@RequestParam String id) {
        Optional<Food> food = foodService.findById(id);

        if (food.isPresent()) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Food found", food.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseHttp<>(204, "No content", null));
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
    public ResponseEntity<ResponseHttp<List<FoodCategory>>> findAllCategories() {
        
        List<FoodCategory> categories = foodService.findAllCategories();
        logger.debug("Categories found: " + categories.size());

        if (!categories.isEmpty()) {
            return ResponseEntity.ok(new ResponseHttp<>(200, "Categories found", categories));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseHttp<>(204, "No content", null));
        }
    }

}