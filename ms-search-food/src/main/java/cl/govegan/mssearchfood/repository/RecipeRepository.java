package cl.govegan.mssearchfood.repository;

import cl.govegan.mssearchfood.model.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    @NonNull
    Page<Recipe> findAll (@NonNull Pageable pageable);

    Page<Recipe> findByTitleNormalizedContaining (@NonNull String query, @NonNull Pageable pageable);

    @Override
    @NonNull
    Optional<Recipe> findById (@NonNull String id);

    @NonNull
    @Query("{ 'specialNeeds' : { $regex: ?0, $options: 'i' } }")
    Page<Recipe> findBySpecialNeedsContaining (@NonNull String specialNeed, @NonNull Pageable pageable);

}