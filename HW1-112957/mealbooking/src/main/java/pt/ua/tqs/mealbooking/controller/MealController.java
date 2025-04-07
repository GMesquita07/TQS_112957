package pt.ua.tqs.mealbooking.controller;

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

    @GetMapping
    public List<MealWithWeatherDTO> getMeals(@RequestParam Long restaurantId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return mealService.getMealsWithWeather(restaurantId, localDate);
    }
}
