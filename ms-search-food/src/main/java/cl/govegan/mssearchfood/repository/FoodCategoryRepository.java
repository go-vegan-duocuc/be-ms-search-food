package cl.govegan.mssearchfood.repository;

import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FoodCategoryRepository extends MongoRepository<FoodCategory, String> {
    List<FoodCategory> findByCategoryNameContainingIgnoreCase (@NonNull String search);

    Optional<FoodCategory> findByCategoryCode (int code);
}
