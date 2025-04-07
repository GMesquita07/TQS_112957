package pt.ua.tqs.mealbooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.tqs.mealbooking.dto.WeatherCacheStats;
import pt.ua.tqs.mealbooking.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/cache-stats")
    public WeatherCacheStats getCacheStats() {
        return weatherService.getCacheStats();
    }
}
