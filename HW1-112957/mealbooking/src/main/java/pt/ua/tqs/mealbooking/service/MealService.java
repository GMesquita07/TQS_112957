package pt.ua.tqs.mealbooking.service;

import org.springframework.stereotype.Service;
import pt.ua.tqs.mealbooking.dto.MealWithWeatherDTO;
import pt.ua.tqs.mealbooking.model.Meal;
import pt.ua.tqs.mealbooking.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final WeatherService weatherService;

    public MealService(MealRepository mealRepository, WeatherService weatherService) {
        this.mealRepository = mealRepository;
        this.weatherService = weatherService;
    }

    public List<MealWithWeatherDTO> getMealsWithWeather(Long restaurantId, LocalDate date) {
        List<Meal> meals = mealRepository.findByRestaurantIdAndDate(restaurantId, date);
        return meals.stream().map(meal -> {
            String forecast;
            try {
                forecast = weatherService.getForecast("Campus Moliceiro", date); // Podes mudar o nome do local
            } catch (Exception e) {
                forecast = "Sem previsão";
            }

            return new MealWithWeatherDTO(
                    meal.getId(),
                    meal.getDescription(),
                    meal.getType(),
                    meal.getDate().toString(),
                    forecast
            );
        }).collect(Collectors.toList());
    }
}
