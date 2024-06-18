package cl.govegan.mssearchfood.service.foodservice;

import cl.govegan.mssearchfood.exception.DatabaseOperationException;
import cl.govegan.mssearchfood.exception.InvalidFoodDataException;
import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.repository.FoodRepository;
import cl.govegan.mssearchfood.service.foodcategoryservice.FoodCategoryService;
import cl.govegan.mssearchfood.utils.request.Normalization;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodCategoryService foodCategoryService;
    private final ObjectMapper objectMapper;

    @Override
    public Page<Food> findAllFoods (Pageable pageable) {
        try {
            return foodRepository.findAll(pageable);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching foods");
        }
    }

    @Override
    public Page<Food> findAllFoodsBySearch (String query, Pageable pageable) {
        try {
            return foodRepository.findByNameNormalizedContaining(query, pageable);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching foods by search query: " + query);
        }
    }

    @Override
    public Optional<Food> findById (String id) {
        try {
            return foodRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching food by ID: " + id);
        }
    }

    @Override
    public Food saveFood (Map<String, String> body) {
        if (body.get("name") == null || body.get("name").isBlank() ||
                body.get("caloriesKcal") == null || body.get("caloriesKcal").isBlank()) {
            throw new InvalidFoodDataException("Name and calories are required");
        }

        FoodCategory category = foodCategoryService.findById(body.get("category"))
                .orElseThrow(() -> new InvalidFoodDataException("Category with the given ID does not exist"));

        Food food = objectMapper.convertValue(body, Food.class);
        food.setNameNormalized(Normalization.normalizeText(food.getName()));
        food.setCategory(category);

        try {
            return foodRepository.save(food);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while saving food");
        }
    }

    @Override
    public Food updateFood (Map<String, String> body) {
        if (body.get("id") == null || body.get("id").isBlank()) {
            throw new InvalidFoodDataException("Id is required");
        }

        String id = body.get("id");
        Optional<Food> existingFoodOpt = foodRepository.findById(id);
        if (existingFoodOpt.isEmpty()) {
            throw new InvalidFoodDataException("Food with the given ID does not exist");
        }

        Food existingFood = existingFoodOpt.get();
        Food updatedFood = objectMapper.convertValue(body, Food.class);

        BeanUtils.copyProperties(updatedFood, existingFood, "id");
        existingFood.setNameNormalized(Normalization.normalizeText(updatedFood.getName()));

        try {
            return foodRepository.save(existingFood);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while updating food");
        }
    }

    @Override
    public void deleteById (String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidFoodDataException("Id is required");
        }

        Optional<Food> existingFood = foodRepository.findById(id);
        if (existingFood.isEmpty()) {
            throw new InvalidFoodDataException("Food with the given ID does not exist");
        }

        try {
            foodRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while deleting food");
        }
    }

    @Override
    public Page<Food> findAllFoodsByCategory (String categoryId, Pageable pageable) {
        try {
            return foodRepository.findByCategoryId(categoryId, pageable);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching foods by category ID: " + categoryId);
        }
    }

}
