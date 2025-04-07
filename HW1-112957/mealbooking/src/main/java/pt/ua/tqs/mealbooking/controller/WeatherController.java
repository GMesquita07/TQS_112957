package pt.ua.tqs.mealbooking.controller;

import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;
import pt.ua.tqs.mealbooking.service.WeatherService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecast")
    public WeatherForecast getForecast(@RequestParam String location, @RequestParam String date) {
        return weatherService.getForecast(location, LocalDate.parse(date));
    }

    @GetMapping("/cache/stats")
    public Map<String, Integer> getCacheStats() {
        return weatherService.getCacheStats();
    }
}
