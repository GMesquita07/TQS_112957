package pt.ua.tqs.mealbooking.service;

import pt.ua.tqs.mealbooking.dto.WeatherForecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherCache {
    private static class CachedForecast {
        WeatherForecast forecast;
        LocalDateTime expiration;

        CachedForecast(WeatherForecast forecast, LocalDateTime expiration) {
            this.forecast = forecast;
            this.expiration = expiration;
        }
    }

    private final Map<LocalDate, CachedForecast> cache = new ConcurrentHashMap<>();
    private final int ttlMinutes = 60; // Time-to-Live: 1 hora
    private int hits = 0;
    private int misses = 0;

    public WeatherForecast get(LocalDate date) {
        CachedForecast entry = cache.get(date);
        if (entry != null && entry.expiration.isAfter(LocalDateTime.now())) {
            hits++;
            return entry.forecast;
        } else {
            if (entry != null) cache.remove(date);
            misses++;
            return null;
        }
    }

    public void put(LocalDate date, WeatherForecast forecast) {
        cache.put(date, new CachedForecast(forecast, LocalDateTime.now().plusMinutes(ttlMinutes)));
    }

    public int getHits() { return hits; }
    public int getMisses() { return misses; }
    public int getTotalRequests() { return hits + misses; }
}
