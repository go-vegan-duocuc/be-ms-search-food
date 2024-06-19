package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.service.foodcategoryservice.FoodCategoryService;
import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import cl.govegan.mssearchfood.web.response.ApiListResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TestFoodCategoryController {

    @Mock
    private FoodCategoryService foodCategoryService;

    @InjectMocks
    private FoodCategoryController foodCategoryController;

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
    @DisplayName("Should return all food categories")
    void getAllFoodCategories () {
        when(foodCategoryService.findAllFoodCategories()).thenReturn(Collections.singletonList(new FoodCategory(
                "1",
                "category",
                1
        )));

        ResponseEntity<ApiListResponse<FoodCategory>> response = foodCategoryController.getAllFoodCategories();

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).findAllFoodCategories();
    }

    @Test
    @DisplayName("Should return food categories by search")
    void getAllFoodCategoriesBySearch () {
        when(foodCategoryService.findByName(anyString())).thenReturn(Collections.singletonList(new FoodCategory(
                "1",
                "category",
                1
        )));

        ResponseEntity<ApiListResponse<FoodCategory>> response = foodCategoryController.getAllFoodCategoriesBySearch("test");

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("Should return food category by id")
    void getFoodCategoryById () {
        when(foodCategoryService.findById(anyString())).thenReturn(java.util.Optional.of(new FoodCategory()));

        ResponseEntity<ApiEntityResponse<FoodCategory>> response = foodCategoryController.getFoodCategoryById("1");

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should return food category by code")
    void getFoodCategoryByCode () {
        when(foodCategoryService.findByCode(anyInt())).thenReturn(java.util.Optional.of(new FoodCategory()));

        ResponseEntity<ApiEntityResponse<FoodCategory>> response = foodCategoryController.getFoodCategoryByCode(1);

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).findByCode(anyInt());
    }

    @Test
    @DisplayName("Should post food category")
    void postFoodCategory () {
        when(foodCategoryService.save(anyMap())).thenReturn(new FoodCategory());

        ResponseEntity<ApiEntityResponse<FoodCategory>> response = foodCategoryController.postFoodCategory(new HashMap<>());

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).save(anyMap());
    }

    @Test
    @DisplayName("Should update food category")
    void updateFoodCategory () {
        when(foodCategoryService.update(anyMap())).thenReturn(new FoodCategory());

        ResponseEntity<ApiEntityResponse<FoodCategory>> response = foodCategoryController.updateFoodCategory(new HashMap<>());

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).update(anyMap());
    }

    @Test
    @DisplayName("Should delete food category")
    void deleteFoodCategory () {
        doNothing().when(foodCategoryService).delete(anyString());

        ResponseEntity<ApiEntityResponse<String>> response = foodCategoryController.deleteFoodCategory("1");

        assertEquals(200, response.getStatusCode().value());
        verify(foodCategoryService, times(1)).delete(anyString());
    }

}
