package cl.govegan.mssearchfood.service.foodservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.repository.FoodRepository;
import cl.govegan.mssearchfood.web.request.foodrequest.FoodRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

   private final FoodRepository foodRepository;
   private final FoodConverter foodConverter;

   /* SEARCH FOOD */

   @Override
   public List<Food> findAll() {
      return foodRepository.findAll();
   }

   @Override
   public List<Food> findByNameContaining(String query) {
      return foodRepository.findByNameContaining(query);
   }

   @Override
   public Optional<Food> findById(String id) {
      return foodRepository.findById(id);
   }

   @Override
   public Food saveFood(FoodRequest foodRequest) {
      Food foodToSave = foodConverter.toFood(foodRequest);
      return foodRepository.save(foodToSave);
   }

   @Override
   public Food updateFood(FoodRequest foodRequest) {
      Food foodToUpdate = foodConverter.toFood(foodRequest);

      if(!foodRepository.existsById(foodToUpdate.getId())) {
         throw new RuntimeException("Food not found");
      }
      return foodRepository.save(foodToUpdate);
   }

   @Override
   public void deleteById(String id) {
      foodRepository.deleteById(id);
   }

}
