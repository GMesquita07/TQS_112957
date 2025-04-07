package pt.ua.tqs.mealbooking.controller;

import pt.ua.tqs.mealbooking.model.Meal;
import pt.ua.tqs.mealbooking.model.MealType;
import pt.ua.tqs.mealbooking.model.Restaurant;
import pt.ua.tqs.mealbooking.repository.MealRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
public class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MealRepository mealRepository;

    @Test
    public void testGetMealsByRestaurantAndDate() throws Exception {
        Restaurant r = new Restaurant("Cantina Central", "Campus Norte");
        r.setId(1L);

        Meal m1 = new Meal(LocalDate.of(2025, 4, 7), MealType.ALMOCO, "Frango", r);

        when(mealRepository.findByRestaurantIdAndDate(1L, LocalDate.of(2025, 4, 7)))
            .thenReturn(List.of(m1));

        mockMvc.perform(get("/api/meals")
                .param("restaurantId", "1")
                .param("date", "2025-04-07"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].description").value("Frango"));
    }
}
