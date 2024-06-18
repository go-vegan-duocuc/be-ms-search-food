package cl.govegan.mssearchfood.exception;

import cl.govegan.mssearchfood.service.recipeservice.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestExceptions {

    @Mock
    private RecipeService mockRecipeService;

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
    void testDatabaseOperationException () {
        DatabaseOperationException databaseOperationException = assertThrows(DatabaseOperationException.class, () -> {
            throw new DatabaseOperationException("Database operation failed");
        });

        assertEquals("Database operation failed", databaseOperationException.getMessage());
    }

    @Test
    void testInvalidFoodDataException () {
        InvalidFoodDataException invalidFoodDataException = assertThrows(InvalidFoodDataException.class, () -> {
            throw new InvalidFoodDataException("Invalid food data");
        });

        assertEquals("Invalid food data", invalidFoodDataException.getMessage());
    }

    @Test
    void testInvalidRecipeDataException () {
        InvalidRecipeDataException invalidFoodDataException = assertThrows(InvalidRecipeDataException.class, () -> {
            throw new InvalidRecipeDataException("Invalid recipe data");
        });

        assertEquals("Invalid recipe data", invalidFoodDataException.getMessage());
    }
}
