package cl.govegan.mssearchfood.controller;

import cl.govegan.mssearchfood.model.recipe.Recipe;
import cl.govegan.mssearchfood.service.recipeservice.RecipeService;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TestRecipeController {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

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
    @DisplayName("Should return all recipes")
    void getAllRecipes () {
        Page<Recipe> recipePage = new PageImpl<>(Collections.singletonList(new Recipe()));
        when(recipeService.findAllRecipes(any())).thenReturn(recipePage);

        ResponseEntity<ApiPageResponse<Recipe>> response = recipeController.getAllRecipes(0, 10);

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).findAllRecipes(any());
    }

    @Test
    @DisplayName("Should return recipes by search")
    void getAllRecipesBySearch () {
        Page<Recipe> recipePage = new PageImpl<>(Collections.singletonList(new Recipe()));
        when(recipeService.findAllRecipesBySearch(anyString(), any())).thenReturn(recipePage);

        ResponseEntity<ApiPageResponse<Recipe>> response = recipeController.getAllFoodsBySearch("test", 0, 10);

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).findAllRecipesBySearch(anyString(), any());
    }

    @Test
    @DisplayName("Should return recipe by id")
    void getRecipeById () {
        when(recipeService.findById(anyString())).thenReturn(java.util.Optional.of(new Recipe()));

        ResponseEntity<ApiEntityResponse<Recipe>> response = recipeController.getRecipeById("1");

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should return recipe by special need")
    void getAllRecipesBySpecialNeeds () {
        Page<Recipe> recipePage = new PageImpl<>(Collections.singletonList(new Recipe()));
        when(recipeService.findAllRecipesBySpecialNeeds(anyString(), any())).thenReturn(recipePage);

        ResponseEntity<ApiPageResponse<Recipe>> response = recipeController.getRecipesBySpecialNeed(Map.of("specialNeed", "sin gluten"), 0, 10);

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).findAllRecipesBySpecialNeeds(anyString(), any());
    }

    @Test
    @DisplayName("Should save recipe")
    void saveRecipe () {
        when(recipeService.saveRecipe(anyMap())).thenReturn(new Recipe());

        ResponseEntity<ApiEntityResponse<Recipe>> response = recipeController.saveRecipe(new HashMap<>());

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).saveRecipe(anyMap());
    }

    @Test
    @DisplayName("Should update recipe")
    void updateRecipe () {
        when(recipeService.updateRecipe(anyMap())).thenReturn(new Recipe());

        ResponseEntity<ApiEntityResponse<Recipe>> response = recipeController.updateRecipe(new HashMap<>());

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).updateRecipe(anyMap());
    }

    @Test
    @DisplayName("Should delete recipe")
    void deleteRecipe () {
        doNothing().when(recipeService).deleteById(anyString());

        ResponseEntity<ApiEntityResponse<String>> response = recipeController.deleteRecipe("1");

        assertEquals(200, response.getStatusCode().value());
        verify(recipeService, times(1)).deleteById(anyString());
    }
}