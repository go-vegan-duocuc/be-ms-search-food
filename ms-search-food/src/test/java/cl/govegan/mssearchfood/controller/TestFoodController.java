package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.service.foodservice.FoodService;
import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import cl.govegan.mssearchfood.web.response.ApiPageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
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
        Page<Food> foodPage = new PageImpl<>(Collections.singletonList(new Food()));
        when(foodService.findAllFoods(any())).thenReturn(foodPage);

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
    @DisplayName("Should return food by id")
    void getFoodById () {
        when(foodService.findById(anyString())).thenReturn(java.util.Optional.of(new Food()));

        ResponseEntity<ApiEntityResponse<Food>> response = foodController.getFoodById("1");

        assertEquals(200, response.getStatusCode().value());
        verify(foodService, times(1)).findById(anyString());
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