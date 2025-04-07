package pt.ua.tqs.mealbooking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.ua.tqs.mealbooking.dto.WeatherCacheStats;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final Map<String, CachedForecast> cache = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    private final long ttlMillis = 60 * 60 * 1000; // 1 hora

    // Estatísticas
    private int totalRequests = 0;
    private int hits = 0;
    private int misses = 0;

    public WeatherForecast getForecast(String city, LocalDate date) {
        totalRequests++;
        String key = city + "_" + date;
        CachedForecast cached = cache.get(key);

        if (cached != null && System.currentTimeMillis() - cached.timestamp < ttlMillis) {
            hits++;
            return cached.forecast;
        }

        misses++;

        String url = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric&lang=pt",
                city, apiKey
        );

        var response = restTemplate.getForObject(url, Map.class);
        if (response == null || !response.containsKey("list")) {
            throw new RuntimeException("Resposta inválida da API de tempo.");
        }

        var list = (java.util.List<Map<String, Object>>) response.get("list");

        WeatherForecast selected = null;

        for (var item : list) {
            long timestamp = ((Number) item.get("dt")).longValue();
            LocalDate itemDate = java.time.Instant.ofEpochSecond(timestamp)
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            if (itemDate.equals(date)) {
                Map<String, Object> main = (Map<String, Object>) item.get("main");
                java.util.List<Map<String, Object>> weatherList = (java.util.List<Map<String, Object>>) item.get("weather");
                String description = (String) weatherList.get(0).get("description");
                double temp = ((Number) main.get("temp")).doubleValue();
                selected = new WeatherForecast(itemDate, description, temp);
                break;
            }
        }

        if (selected == null) {
            throw new RuntimeException("Não foi possível encontrar previsão para esta data.");
        }

        cache.put(key, new CachedForecast(selected, System.currentTimeMillis()));
        return selected;
    }

    public WeatherCacheStats getCacheStats() {
        return new WeatherCacheStats(totalRequests, hits, misses);
    }

    private static class CachedForecast {
        WeatherForecast forecast;
        long timestamp;

        CachedForecast(WeatherForecast forecast, long timestamp) {
            this.forecast = forecast;
            this.timestamp = timestamp;
        }
    }
}
