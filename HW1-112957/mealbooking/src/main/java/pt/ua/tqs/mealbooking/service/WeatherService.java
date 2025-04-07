package pt.ua.tqs.mealbooking.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    private static final long CACHE_TTL_MINUTES = 60;

    // ✅ Chave agora é uma String: "location_date"
    private final Map<String, WeatherCacheEntry> cache = new HashMap<>();

    private int totalRequests = 0;
    private int hits = 0;
    private int misses = 0;

    public String getForecast(String location, LocalDate date) {
        totalRequests++;
        String key = location + "_" + date;

        WeatherCacheEntry cached = cache.get(key);
        if (cached != null && !cached.isExpired()) {
            hits++;
            return cached.getForecast();
        }

        // Simula chamada à API externa
        String forecast = simulateWeatherApi(location, date);
        cache.put(key, new WeatherCacheEntry(forecast));
        misses++;
        return forecast;
    }

    public Map<String, Integer> getCacheStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalRequests", totalRequests);
        stats.put("hits", hits);
        stats.put("misses", misses);
        return stats;
    }

    // Simulação da previsão do tempo
    private String simulateWeatherApi(String location, LocalDate date) {
        return "Sol e 20ºC em " + location + " no dia " + date;
    }

    // Classe interna para guardar entradas da cache com TTL
    private static class WeatherCacheEntry {
        private final String forecast;
        private final LocalDateTime timestamp;

        public WeatherCacheEntry(String forecast) {
            this.forecast = forecast;
            this.timestamp = LocalDateTime.now();
        }

        public String getForecast() {
            return forecast;
        }

        public boolean isExpired() {
            return timestamp.plusMinutes(CACHE_TTL_MINUTES).isBefore(LocalDateTime.now());
        }
    }
}
