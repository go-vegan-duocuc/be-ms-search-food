package cl.govegan.mssearchfood.service.recipeservice;

import cl.govegan.mssearchfood.model.recipe.Recipe;
import cl.govegan.mssearchfood.repository.RecipeRepository;
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

class TestRecipeServiceImpl {

    private final Map<String, Object> body = Map.of(
            "id", "1",
            "title", "Recipe1",
            "titleNormalized", "recipe1"
    );

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private NormalizationWrapper normalizationWrapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

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
    void shouldReturnAllRecipes () {
        Pageable pageable = PageRequest.of(0, 10);
        when(recipeRepository.findAll(pageable)).thenReturn(Page.empty());

        assertTrue(recipeService.findAllRecipes(pageable).isEmpty());

        verify(recipeRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return recipes by search")
    void shouldReturnRecipesBySearch () {
        Pageable pageable = PageRequest.of(0, 10);
        when(recipeRepository.findByTitleNormalizedContaining("Vegan", pageable)).thenReturn(Page.empty());

        assertTrue(recipeService.findAllRecipesBySearch("Vegan", pageable).isEmpty());

        verify(recipeRepository, times(1)).findByTitleNormalizedContaining(anyString(), eq(pageable));
    }

    @Test
    @DisplayName("Should return recipe by id")
    void shouldReturnRecipeById () {
        Recipe recipe = new Recipe();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        assertEquals(recipe, recipeService.findById("1").get());

        verify(recipeRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should save recipe")
    void shouldSaveRecipe () {
        Recipe recipe = Recipe.builder().id("1").title("Recipe1").titleNormalized("recipe1").build();
        when(objectMapper.convertValue(body, Recipe.class)).thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(normalizationWrapper.normalizeText(anyString())).thenReturn("recipe1");

        Recipe savedRecipe = recipeService.saveRecipe(body);

        assertEquals(recipe, savedRecipe);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    @DisplayName("Should update recipe")
    void shouldUpdateRecipe () {
        Recipe recipe = Recipe.builder().id("1").title("Recipe1").titleNormalized("recipe1").build();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
        when(objectMapper.convertValue(any(Map.class), eq(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(normalizationWrapper.normalizeText(anyString())).thenReturn("recipe1");

        assertEquals(recipe, recipeService.updateRecipe(body));

        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    @DisplayName("Should delete recipe by id")
    void shouldDeleteRecipeById () {
        Recipe recipe = new Recipe();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        recipeService.deleteById("1");

        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Should return all recipes by special need")
    void shouldReturnAllRecipesBySpecialNeed () {
        Pageable pageable = PageRequest.of(0, 10);
        when(recipeRepository.findBySpecialNeedsContaining("sin gluten", pageable)).thenReturn(Page.empty());

        assertTrue(recipeService.findAllRecipesBySpecialNeeds("sin gluten", pageable).isEmpty());

        verify(recipeRepository, times(1)).findBySpecialNeedsContaining(anyString(), eq(pageable));
    }
}