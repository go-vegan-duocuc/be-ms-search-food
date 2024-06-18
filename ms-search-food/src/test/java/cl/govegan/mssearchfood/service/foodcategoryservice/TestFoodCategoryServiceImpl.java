package cl.govegan.mssearchfood.service.foodcategoryservice;

import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.repository.FoodCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestFoodCategoryServiceImpl {

    private final Map<String, String> body = Map.of(
            "id", "1",
            "categoryName", "category",
            "categoryCode", "1"
    );

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FoodCategoryServiceImpl foodCategoryService;

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
    void shouldReturnAllFoodCategories () {
        when(foodCategoryRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(foodCategoryService.findAllFoodCategories().isEmpty());

        verify(foodCategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return food category by id")
    void shouldReturnFoodCategoryById () {
        FoodCategory foodCategory = new FoodCategory();
        when(foodCategoryRepository.findById(anyString())).thenReturn(Optional.of(foodCategory));

        Optional<FoodCategory> optionalFoodCategory = foodCategoryService.findById("1");
        assertTrue(optionalFoodCategory.isPresent());
        assertEquals(foodCategory, optionalFoodCategory.get());

        verify(foodCategoryRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should return food categories by name")
    void shouldReturnFoodCategoriesByName () {
        when(foodCategoryRepository.findByCategoryNameContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        assertTrue(foodCategoryService.findByName("Vegan").isEmpty());

        verify(foodCategoryRepository, times(1)).findByCategoryNameContainingIgnoreCase(anyString());
    }

    @Test
    @DisplayName("Should return food category by code")
    void shouldReturnFoodCategoryByCode () {
        FoodCategory foodCategory = FoodCategory.builder().categoryName("Food").categoryCode(1).build();
        when(foodCategoryRepository.findByCategoryCode(anyInt())).thenReturn(Optional.of(foodCategory));

        Optional<FoodCategory> optionalFoodCategory = foodCategoryService.findByCode(1);

        assertTrue(optionalFoodCategory.isPresent());
        assertEquals(foodCategory, optionalFoodCategory.get());

        verify(foodCategoryRepository, times(1)).findByCategoryCode(anyInt());
    }

    @Test
    @DisplayName("Should save food category")
    void shouldSaveFoodCategory () {
        FoodCategory foodCategory = new FoodCategory();
        when(objectMapper.convertValue(body, FoodCategory.class)).thenReturn(foodCategory);
        when(foodCategoryRepository.save(any(FoodCategory.class))).thenReturn(foodCategory);

        assertEquals(foodCategory, foodCategoryService.save(body));

        verify(foodCategoryRepository, times(1)).save(any(FoodCategory.class));
    }

    @Test
    @DisplayName("Should update food category")
    void shouldUpdateFoodCategory () {
        FoodCategory foodCategory = new FoodCategory();
        when(foodCategoryRepository.findById(anyString())).thenReturn(Optional.of(foodCategory));
        when(objectMapper.convertValue(any(Map.class), eq(FoodCategory.class))).thenReturn(foodCategory);
        when(foodCategoryRepository.save(any(FoodCategory.class))).thenReturn(foodCategory);

        assertEquals(foodCategory, foodCategoryService.update(body));

        verify(foodCategoryRepository, times(1)).findById(anyString());
        verify(foodCategoryRepository, times(1)).save(any(FoodCategory.class));
    }

    @Test
    @DisplayName("Should delete food category by id")
    void shouldDeleteFoodCategoryById () {
        FoodCategory foodCategory = new FoodCategory();
        when(foodCategoryRepository.findById(anyString())).thenReturn(Optional.of(foodCategory));

        foodCategoryService.delete("1");

        verify(foodCategoryRepository, times(1)).findById(anyString());
        verify(foodCategoryRepository, times(1)).deleteById(anyString());
    }
}