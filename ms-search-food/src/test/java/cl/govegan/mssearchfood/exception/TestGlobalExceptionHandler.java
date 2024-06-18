package cl.govegan.mssearchfood.exception;

import cl.govegan.mssearchfood.service.foodservice.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestGlobalExceptionHandler {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @Test
    void testInvalidFoodDataException () throws Exception {
        doThrow(new InvalidFoodDataException("Id is required"))
                .when(foodService).updateFood(anyMap());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"\",\"name\": \"\", \"caloriesKcal\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Id is required"));
    }
}