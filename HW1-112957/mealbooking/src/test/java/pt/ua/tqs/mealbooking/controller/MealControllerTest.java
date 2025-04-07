package pt.ua.tqs.mealbooking.controller;

import pt.ua.tqs.mealbooking.dto.MealWithWeatherDTO;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;
import pt.ua.tqs.mealbooking.model.MealType;
import pt.ua.tqs.mealbooking.service.MealService;

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
    private MealService mealService;

    @Test
    public void testGetMealsWithWeather() throws Exception {
        LocalDate date = LocalDate.of(2025, 4, 7);
        WeatherForecast forecast = new WeatherForecast(date, "Sol", 20.0);

        MealWithWeatherDTO dto = new MealWithWeatherDTO(
                1L,
                "Frango assado",
                MealType.ALMOCO,
                date,
                forecast
        );

        when(mealService.getMealsWithWeather(1L, date)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/meals/weather")
                        .param("restaurantId", "1")
                        .param("date", "2025-04-07"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Frango assado"))
                .andExpect(jsonPath("$[0].type").value("ALMOCO"))
                .andExpect(jsonPath("$[0].weatherForecast.summary").value("Sol"))
                .andExpect(jsonPath("$[0].weatherForecast.temperature").value(20.0));
    }
}
