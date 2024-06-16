package cl.govegan.mssearchfood.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.service.foodservice.FoodService;
import cl.govegan.mssearchfood.utils.custompage.PaginationUtils;
import cl.govegan.mssearchfood.utils.response.ResponseBuilder;
import cl.govegan.mssearchfood.web.request.foodrequest.FoodRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping()
    public ResponseEntity<ResponseBuilder<Page<Food>>> findAllFoods(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<Food> foodsResult = foodService.findAll();

        return ResponseBuilder.buildResponse("All foods found", "No data found", PaginationUtils.createCustomPage(foodsResult, page, size));
    }

    @GetMapping("/findBySearch")
    public ResponseEntity<ResponseBuilder<Page<Food>>> findBySearch(
            @RequestParam String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<Food> foodsResult = foodService.findByNameContaining(search);

        return ResponseBuilder.buildResponse("Food found by search: " + search + ".", "No data found by search: " + search + ".",PaginationUtils.createCustomPage(foodsResult, page, size));
    }

    @GetMapping("/findById")
    public ResponseEntity<ResponseBuilder<Food>> findFoodById(@RequestParam String id) {
        Optional<Food> foodResult = foodService.findById(id);

        return ResponseBuilder.buildResponse("Food found by ID: " + id + ".", "No data found by ID: "+ id + ".", foodResult);
    }

    @PostMapping()
    public ResponseEntity<ResponseBuilder<Food>> saveFood(@RequestBody FoodRequest foodRequest) {
        Food savedFood = foodService.saveFood(foodRequest);

        return ResponseBuilder.buildResponse("Food saved successfully", "Error saving food", savedFood);
    }

    @PatchMapping()
    public ResponseEntity<ResponseBuilder<Food>> updateFood(@RequestBody FoodRequest foodRequest) {
        Food updatedFood = foodService.updateFood(foodRequest);

        return ResponseBuilder.buildResponse("Food updated successfully", "Error updating food", updatedFood);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseBuilder<String>> deleteFood(@RequestParam String id) {
        foodService.deleteById(id);

        return ResponseBuilder.buildResponse("Food deleted successfully", "Error deleting food", id);
    }

}