package cl.govegan.mssearchfood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import cl.govegan.mssearchfood.model.food.Food;

public interface FoodRepository extends MongoRepository<Food, String> {

   @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
   List<Food> findByNameContaining(@NonNull String query);

   @NonNull
   @Override
   Optional<Food> findById(@NonNull String id);
   
}
