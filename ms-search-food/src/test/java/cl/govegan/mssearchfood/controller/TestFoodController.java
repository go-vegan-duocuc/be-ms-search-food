package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.service.foodservice.FoodService;
import cl.govegan.mssearchfood.utils.page.Paginator;
import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import cl.govegan.mssearchfood.web.response.ApiPageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class TestFoodController {

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp () {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown () throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Should return status OK")
    void getStatus () {
        ResponseEntity<String> response = foodController.getStatus();

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Should return all foods")
    void getAllFoods () {
        // Create a non-null Food object
        Food food = mock(Food.class);
        Page<Food> foodPage = new PageImpl<>(Collections.singletonList(food));
        when(foodService.findAllFoods(Paginator.getPageable(0, 10))).thenReturn(foodPage);

        ResponseEntity<ApiPageResponse<Food>> response = foodController.getAllFoods(0, 10);

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).findAllFoods(any());
    }

    @Test
    @DisplayName("Should return foods by search")
    void getAllFoodsBySearch () {
        Page<Food> foodPage = new PageImpl<>(Collections.singletonList(new Food()));
        when(foodService.findAllFoodsBySearch(anyString(), any())).thenReturn(foodPage);

        ResponseEntity<ApiPageResponse<Food>> response = foodController.getAllFoodsBySearch("test", 0, 10);

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).findAllFoodsBySearch(anyString(), any());
    }

    @Test
    @DisplayName("Should return food by id when food exists")
    void getFoodById_WhenFoodExists () {
        Food food = mock(Food.class);
        when(foodService.findById("1")).thenReturn(Optional.of(food));

        ResponseEntity<ApiEntityResponse<Food>> response = foodController.getFoodById("1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(food, response.getBody().getData());
        verify(foodService, times(1)).findById("1");
    }

    @Test
    @DisplayName("Should return not found when food does not exist")
    void getFoodById_WhenFoodDoesNotExist () {
        when(foodService.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<ApiEntityResponse<Food>> response = foodController.getFoodById("1");

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody().getData());
        verify(foodService, times(1)).findById("1");
    }

    @Test
    @DisplayName("Should post food")
    void postFood () {
        when(foodService.saveFood(anyMap())).thenReturn(new Food());

        ResponseEntity<ApiEntityResponse<Food>> response = foodController.postFood(new HashMap<>());

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).saveFood(anyMap());
    }

    @Test
    @DisplayName("Should update food")
    void patchFood () {
        when(foodService.updateFood(anyMap())).thenReturn(new Food());

        ResponseEntity<ApiEntityResponse<Food>> response = foodController.patchFood(new HashMap<>());

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).updateFood(anyMap());
    }

    @Test
    @DisplayName("Should delete food")
    void deleteFood () {
        doNothing().when(foodService).deleteById(anyString());

        ResponseEntity<ApiEntityResponse<String>> response = foodController.deleteFood("1");

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Should return food by category")
    void getFoodByCategory () {
        Page<Food> foodPage = new PageImpl<>(Collections.singletonList(new Food()));
        when(foodService.findAllFoodsByCategory(anyString(), any())).thenReturn(foodPage);

        ResponseEntity<ApiPageResponse<Food>> response = foodController.getAllFoodsByCategory("1", 0, 10);

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).findAllFoodsByCategory(anyString(), any());
    }
}