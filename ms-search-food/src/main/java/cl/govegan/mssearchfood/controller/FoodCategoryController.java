package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.service.foodcategoryservice.FoodCategoryService;
import cl.govegan.mssearchfood.utils.message.ErrorMessage;
import cl.govegan.mssearchfood.utils.message.SuccessMessage;
import cl.govegan.mssearchfood.utils.response.ResponseBuilder;
import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import cl.govegan.mssearchfood.web.response.ApiListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/food-categories")
@RequiredArgsConstructor
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    @GetMapping()
    public ResponseEntity<ApiListResponse<FoodCategory>> getAllFoodCategories () {

        List<FoodCategory> foodCategories = foodCategoryService.findAllFoodCategories();

        return ResponseBuilder.buildListResponse((!foodCategories.isEmpty()) ?
                        SuccessMessage.DATA_FOUND
                        : ErrorMessage.DATA_NOT_FOUND,
                foodCategories);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiListResponse<FoodCategory>> getAllFoodCategoriesBySearch (
            @RequestParam String search) {

        List<FoodCategory> foodCategories = foodCategoryService.findByName(search);

        return ResponseBuilder.buildListResponse((!foodCategories.isEmpty()) ?
                        SuccessMessage.DATA_FOUND_BY_SEARCH + search
                        : ErrorMessage.DATA_NOT_FOUND,
                foodCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<FoodCategory>> getFoodCategoryById (@PathVariable String id) {

        FoodCategory foodCategoryResult = foodCategoryService.findById(id).orElse(null);

        return ResponseBuilder.buildEntityResponse((foodCategoryResult != null) ?
                        SuccessMessage.DATA_FOUND_BY_ID + id
                        : ErrorMessage.DATA_NOT_FOUND,
                foodCategoryResult);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiEntityResponse<FoodCategory>> getFoodCategoryByCode (@PathVariable int code) {

        FoodCategory foodCategoryResult = foodCategoryService.findByCode(code).orElse(null);

        return ResponseBuilder.buildEntityResponse((foodCategoryResult != null) ?
                        SuccessMessage.DATA_FOUND_BY_SEARCH + code
                        : ErrorMessage.DATA_NOT_FOUND,
                foodCategoryResult);
    }

    @PostMapping()
    public ResponseEntity<ApiEntityResponse<FoodCategory>> postFoodCategory (@RequestBody Map<String, String> body) {

        FoodCategory foodCategoryResult = foodCategoryService.save(body);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_SAVED, foodCategoryResult);
    }

    @PatchMapping()
    public ResponseEntity<ApiEntityResponse<FoodCategory>> updateFoodCategory (@RequestBody Map<String, String> body) {

        FoodCategory foodCategoryResult = foodCategoryService.update(body);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_UPDATED, foodCategoryResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<String>> deleteFoodCategory (@PathVariable String id) {

        foodCategoryService.delete(id);

        return ResponseBuilder.buildEntityResponse(SuccessMessage.DATA_DELETED, id);
    }

}
