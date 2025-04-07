package pt.ua.tqs.mealbooking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final long CACHE_TTL_MS = 60 * 60 * 1000; // 1 hora

    private final Map<String, CachedForecast> cache = new HashMap<>();
    private int totalRequests = 0;
    private int hits = 0;
    private int misses = 0;

    public WeatherForecast getForecast(String location, LocalDate date) {
        totalRequests++;
        String key = location + "_" + date;

        CachedForecast cached = cache.get(key);
        if (cached != null && !cached.isExpired()) {
            hits++;
            return cached.getForecast();
        }

        String city = resolveCity(location); // traduzir nomes tipo "Cantina Central" para "Aveiro", etc.

        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", city + ",PT")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", "pt")
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
        List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("list");

        for (Map<String, Object> entry : list) {
            String dtTxt = (String) entry.get("dt_txt");
            if (dtTxt != null && dtTxt.startsWith(date.toString())) {
                Map<String, Object> main = (Map<String, Object>) entry.get("main");
                List<Map<String, Object>> weatherList = (List<Map<String, Object>>) entry.get("weather");

                double temp = (main != null && main.containsKey("temp")) ? ((Number) main.get("temp")).doubleValue() : 0.0;
                String description = (weatherList != null && !weatherList.isEmpty()) ?
                        (String) weatherList.get(0).get("description") : "sem info";

                WeatherForecast forecast = new WeatherForecast(date, description, temp);
                cache.put(key, new CachedForecast(forecast, System.currentTimeMillis()));
                misses++;
                return forecast;
            }
        }

        throw new RuntimeException("Não foi possível obter previsão para a data: " + date);
    }

    private String resolveCity(String location) {
        if (location == null) return "Aveiro";
        location = location.toLowerCase();

        if (location.contains("central")) return "Aveiro";
        if (location.contains("santiago")) return "Aveiro";

        return location; // fallback se for já o nome da cidade
    }

    public Map<String, Integer> getCacheStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalRequests", totalRequests);
        stats.put("hits", hits);
        stats.put("misses", misses);
        return stats;
    }

    private static class CachedForecast {
        private final WeatherForecast forecast;
        private final long timestamp;

        public CachedForecast(WeatherForecast forecast, long timestamp) {
            this.forecast = forecast;
            this.timestamp = timestamp;
        }

        public WeatherForecast getForecast() {
            return forecast;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_TTL_MS;
        }
    }
}
