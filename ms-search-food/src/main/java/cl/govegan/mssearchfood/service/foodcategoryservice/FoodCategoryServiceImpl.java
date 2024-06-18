package cl.govegan.mssearchfood.service.foodcategoryservice;

import cl.govegan.mssearchfood.exception.DatabaseOperationException;
import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.repository.FoodCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<FoodCategory> findAllFoodCategories () {
        try {
            return foodCategoryRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching food categories");
        }
    }

    @Override
    public Optional<FoodCategory> findById (String id) {
        try {
            return foodCategoryRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching food category by ID: " + id);
        }
    }

    @Override
    public List<FoodCategory> findByName (String name) {
        try {
            return foodCategoryRepository.findByCategoryNameContainingIgnoreCase(name);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching food category by name: " + name);
        }
    }

    @Override
    public Optional<FoodCategory> findByCode (int code) {
        try {
            return foodCategoryRepository.findByCategoryCode(code);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while fetching food category by code: " + code);
        }
    }

    @Override
    public FoodCategory save (Map<String, String> body) {
        if (body.get("categoryName") == null || body.get("categoryName").isBlank()) {
            throw new DatabaseOperationException("Category name is required");
        }

        FoodCategory newFoodCategory = objectMapper.convertValue(body, FoodCategory.class);

        try {
            return foodCategoryRepository.save(newFoodCategory);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while saving food category");
        }
    }

    @Override
    public FoodCategory update (@RequestBody Map<String, String> body) {
        if (body.get("id") == null || body.get("id").isBlank()) {
            throw new DatabaseOperationException("ID is required");
        }

        String id = body.get("id");
        Optional<FoodCategory> foodCategoryOpt = findById(id);
        if (foodCategoryOpt.isEmpty()) {
            throw new DatabaseOperationException("Food category not found by ID: " + id);
        }

        FoodCategory existingFoodCategory = foodCategoryOpt.get();
        FoodCategory updatedFoodCategory = objectMapper.convertValue(body, FoodCategory.class);

        BeanUtils.copyProperties(updatedFoodCategory, existingFoodCategory, "id");

        try {
            return foodCategoryRepository.save(existingFoodCategory);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while updating food category");
        }
    }

    @Override
    public void delete (String id) {
        if (id == null || id.isBlank()) {
            throw new DatabaseOperationException("ID is required");
        }

        Optional<FoodCategory> foodCategoryOpt = findById(id);
        if (foodCategoryOpt.isEmpty()) {
            throw new DatabaseOperationException("Food category not found by ID: " + id);
        }

        try {
            foodCategoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error while deleting food category by ID: " + id);
        }
    }
}
