package cl.govegan.mssearchfood.service.foodservice;

import cl.govegan.mssearchfood.model.food.Food;
import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.repository.FoodRepository;
import cl.govegan.mssearchfood.service.foodcategoryservice.FoodCategoryService;
import cl.govegan.mssearchfood.utils.request.NormalizationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TestFoodServiceImpl {

    private final Map<String, String> body = Map.of(
            "id", "1",
            "name", "Food2",
            "caloriesKcal", "100",
            "category", "1",
            "nameNormalized", "food2"
    );

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodCategoryService foodCategoryService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private NormalizationWrapper normalizationWrapper;

    @InjectMocks
    private FoodServiceImpl foodService;

    @Mock


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
    @DisplayName("Should return all foods")
    void shouldReturnAllFoods () {
        Pageable pageable = PageRequest.of(0, 10);
        when(foodRepository.findAll(pageable)).thenReturn(Page.empty());

        assertTrue(foodService.findAllFoods(pageable).isEmpty());

        verify(foodRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return foods by search")
    void shouldReturnFoodsBySearch () {
        Pageable pageable = PageRequest.of(0, 10);
        when(foodRepository.findByNameNormalizedContaining("Vegan", pageable)).thenReturn(Page.empty());

        assertTrue(foodService.findAllFoodsBySearch("Vegan", pageable).isEmpty());

        verify(foodRepository, times(1)).findByNameNormalizedContaining(anyString(), eq(pageable));
    }

    @Test
    @DisplayName("Should return food by id")
    void shouldReturnFoodById () {
        Food food = new Food();
        when(foodRepository.findById(anyString())).thenReturn(Optional.of(food));

        assertEquals(food, foodService.findById("1").get());

        verify(foodRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should save food")
    void shouldSaveFood () {
        Food food = Food.builder().id("1").name("Food").caloriesKcal(100).category(FoodCategory.builder().id("1").build()).nameNormalized("food").build();
        when(foodCategoryService.findById(anyString())).thenReturn(Optional.of(FoodCategory.builder().id("1").build()));
        when(objectMapper.convertValue(body, Food.class)).thenReturn(food);
        when(foodRepository.save(any(Food.class))).thenReturn(food);
        when(normalizationWrapper.normalizeText(anyString())).thenReturn("food");

        Food savedFood = foodService.saveFood(body);

        assertEquals(food, savedFood);
        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    @DisplayName("Should update food")
    void shouldUpdateFood () {
        Food food = Food.builder().id("1").name("Food").caloriesKcal(100).category(FoodCategory.builder().id("1").build()).nameNormalized("food").build();
        when(foodRepository.findById(anyString())).thenReturn(Optional.of(food));
        when(objectMapper.convertValue(any(Map.class), eq(Food.class))).thenReturn(food);
        when(foodRepository.save(any(Food.class))).thenReturn(food);
        when(normalizationWrapper.normalizeText(anyString())).thenReturn("food");

        assertEquals(food, foodService.updateFood(body));

        verify(foodRepository, times(1)).findById(anyString());
        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    @DisplayName("Should delete food by id")
    void shouldDeleteFoodById () {
        Food food = new Food();
        when(foodRepository.findById(anyString())).thenReturn(Optional.of(food));

        foodService.deleteById("1");

        verify(foodRepository, times(1)).findById(anyString());
        verify(foodRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Should return foods by category")
    void shouldReturnFoodsByCategory () {
        Pageable pageable = PageRequest.of(0, 10);
        when(foodRepository.findByCategoryId("1", pageable)).thenReturn(Page.empty());

        assertTrue(foodService.findAllFoodsByCategory("1", pageable).isEmpty());

        verify(foodRepository, times(1)).findByCategoryId(anyString(), eq(pageable));
    }
}