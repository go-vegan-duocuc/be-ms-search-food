package cl.govegan.mssearchfood.service.foodcategoryservice;

import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface FoodCategoryService {

    List<FoodCategory> findAllFoodCategories ();

    Optional<FoodCategory> findById (String id);

    List<FoodCategory> findByName (String name);

    Optional<FoodCategory> findByCode (int code);

    FoodCategory save (Map<String, String> body);

    FoodCategory update (Map<String, String> body);

    void delete (String id);

}
