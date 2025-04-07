package pt.ua.tqs.mealbooking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.tqs.mealbooking.dto.MealWithWeatherDTO;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;
import pt.ua.tqs.mealbooking.model.Meal;
import pt.ua.tqs.mealbooking.model.MealType;
import pt.ua.tqs.mealbooking.model.Restaurant;
import pt.ua.tqs.mealbooking.repository.MealRepository;
import pt.ua.tqs.mealbooking.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MealServiceWithMockedWeatherTest {

    private MealService mealService;
    private MealRepository mealRepository;
    private RestaurantRepository restaurantRepository;
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        mealRepository = mock(MealRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        weatherService = mock(WeatherService.class);
        mealService = new MealService(mealRepository, restaurantRepository, weatherService);
    }

    @Test
    public void testGetMealsWithWeather_usesMockedForecast() {
        LocalDate date = LocalDate.of(2025, 4, 7);
        Restaurant restaurant = new Restaurant("Cantina Central", "Campus Norte");
        restaurant.setId(1L);
        Meal meal = new Meal(date, MealType.ALMOCO, "Frango", restaurant);
        meal.setId(1L);

        WeatherForecast forecast = new WeatherForecast(date, "Sol", 20.0);

        when(mealRepository.findByRestaurantIdAndDate(1L, date)).thenReturn(Collections.singletonList(meal));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(weatherService.getForecast("Campus Norte", date)).thenReturn(forecast);

        var result = mealService.getMealsWithWeather(1L, date);

        assertEquals(1, result.size());
        assertEquals("Frango", result.get(0).getDescription());
        assertEquals("Sol", result.get(0).getWeatherForecast().getSummary());
    }
}