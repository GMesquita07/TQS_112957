package pt.ua.tqs.mealbooking.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.mealbooking.dto.MealWithWeatherDTO;
import pt.ua.tqs.mealbooking.service.MealService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/weather")
    public List<MealWithWeatherDTO> getMealsWithWeather(
            @RequestParam Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return mealService.getMealsWithWeather(restaurantId, date);
    }
}

