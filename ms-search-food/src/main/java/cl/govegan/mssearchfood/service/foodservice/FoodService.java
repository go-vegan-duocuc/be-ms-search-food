package cl.govegan.mssearchfood.service.foodservice;

import cl.govegan.mssearchfood.model.food.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface FoodService {

    Page<Food> findAllFoods (Pageable pageable);

    Page<Food> findAllFoodsBySearch (String query, Pageable pageable);

    Optional<Food> findById (String id);

    Food saveFood (Map<String, String> body);

    Food updateFood (Map<String, String> body);

    void deleteById (String id);

    Page<Food> findAllFoodsByCategory (String categoryId, Pageable pageable);

}