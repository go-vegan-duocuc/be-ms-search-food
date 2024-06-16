package cl.govegan.mssearchfood.service.foodservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.web.request.foodrequest.FoodRequest;

@Service
public interface FoodService {

   public List<Food> findAll();

   public List<Food> findByNameContaining(String query);

   public Optional<Food> findById(String id);

   public Food saveFood (FoodRequest foodRequest);

   public Food updateFood (FoodRequest foodRequest);

   public void deleteById(String id);
    
}