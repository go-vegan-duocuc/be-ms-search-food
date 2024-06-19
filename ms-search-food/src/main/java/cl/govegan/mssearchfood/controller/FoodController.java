package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.service.foodservice.FoodService;
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
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus () {
        return ResponseEntity.ok("OK");
    }

    @GetMapping()
    public ResponseEntity<ApiPageResponse<Food>> getAllFoods (
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Food> foodsResult = foodService.findAllFoods(Paginator.getPageable(page, size));

        return ResponseBuilder.buildPageResponse((!foodsResult.isEmpty()) ?
                        SuccessMessage.DATA_FOUND
                        : ErrorMessage.DATA_NOT_FOUND,
                foodsResult);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiPageResponse<Food>> getAllFoodsBySearch (
            @RequestParam String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Food> foodsResult = foodService.findAllFoodsBySearch(Normalization.normalizeText(name), Paginator.getPageable(page, size));

        return ResponseBuilder.buildPageResponse((!foodsResult.isEmpty()) ?
                        SuccessMessage.DATA_FOUND_BY_SEARCH + name
                        : ErrorMessage.DATA_NOT_FOUND,
                foodsResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<Food>> getFoodById (@PathVariable String id) {

        Food foodResult = foodService.findById(id).orElse(null);

        return ResponseBuilder.buildEntityResponse((foodResult != null) ?
                        SuccessMessage.DATA_FOUND_BY_ID + id
                        : ErrorMessage.DATA_NOT_FOUND_BY_ID,
                foodResult);
    }

    @PostMapping()
    public ResponseEntity<ApiEntityResponse<Food>> postFood (@RequestBody Map<String, String> body) {

        Food savedFood = foodService.saveFood(body);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_SAVED, savedFood);
    }

    @PatchMapping()
    public ResponseEntity<ApiEntityResponse<Food>> patchFood (@RequestBody Map<String, String> body) {

        Food updatedFood = foodService.updateFood(body);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_UPDATED, updatedFood);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<String>> deleteFood (@PathVariable String id) {

        foodService.deleteById(id);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_DELETED, id);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiPageResponse<Food>> getAllFoodsByCategory (
            @PathVariable String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Food> foodsResult = foodService.findAllFoodsByCategory(id, Paginator.getPageable(page, size));

        return ResponseBuilder.buildPageResponse((!foodsResult.isEmpty()) ?
                        SuccessMessage.DATA_FOUND_BY_ID + id
                        : ErrorMessage.DATA_NOT_FOUND_BY_ID + id,
                foodsResult);
    }

}