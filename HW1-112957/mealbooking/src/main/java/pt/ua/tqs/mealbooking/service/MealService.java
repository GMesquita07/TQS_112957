package pt.ua.tqs.mealbooking.service;

import org.springframework.stereotype.Service;
import pt.ua.tqs.mealbooking.dto.MealWithWeatherDTO;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;
import pt.ua.tqs.mealbooking.model.Meal;
import pt.ua.tqs.mealbooking.model.Restaurant;
import pt.ua.tqs.mealbooking.repository.MealRepository;
import pt.ua.tqs.mealbooking.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository; // <-- ADICIONADO
    private final WeatherService weatherService;

    public MealService(MealRepository mealRepository, RestaurantRepository restaurantRepository, WeatherService weatherService) {
        this.mealRepository = mealRepository;
        this.restaurantRepository = restaurantRepository;
        this.weatherService = weatherService;
    }

    public List<MealWithWeatherDTO> getMealsWithWeather(Long restaurantId, LocalDate date) {
        List<Meal> meals = mealRepository.findByRestaurantIdAndDate(restaurantId, date);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    
        return meals.stream().map(meal -> {
            WeatherForecast forecast = weatherService.getForecast(restaurant.getLocation(), meal.getDate());
            return new MealWithWeatherDTO(
                    meal.getId(),
                    meal.getDescription(),
                    meal.getType(),
                    meal.getDate(),
                    forecast
            );
        }).collect(Collectors.toList());
    }
    
}
