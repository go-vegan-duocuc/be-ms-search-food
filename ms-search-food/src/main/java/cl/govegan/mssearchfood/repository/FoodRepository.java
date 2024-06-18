package cl.govegan.mssearchfood.repository;

import cl.govegan.mssearchfood.model.food.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface FoodRepository extends MongoRepository<Food, String> {

    @NonNull
    Page<Food> findAll (@NonNull Pageable pageable);

    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    Page<Food> findByNameContaining (@NonNull String query, @NonNull Pageable pageable);

    Page<Food> findByNameNormalizedContaining (@NonNull String query, @NonNull Pageable pageable);

    @NonNull
    @Override
    Optional<Food> findById (@NonNull String id);

    @Query("{ 'category.id' : ?0 }")
    Page<Food> findByCategoryId (@NonNull String categoryId, @NonNull Pageable pageable);

}
