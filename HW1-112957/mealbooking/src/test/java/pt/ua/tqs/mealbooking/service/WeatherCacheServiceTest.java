package pt.ua.tqs.mealbooking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.tqs.mealbooking.dto.WeatherForecast;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherCacheServiceTest {

    private WeatherCache cache;

    @BeforeEach
    void setup() {
        cache = new WeatherCache();
    }

    @Test
    void testCacheStoresAndRetrieves() {
        LocalDate today = LocalDate.now();
        WeatherForecast forecast = new WeatherForecast(today, "Céu limpo", 23.5);

        // Colocar na cache
        cache.put(today, forecast);

        // Primeira chamada: deve estar na cache
        WeatherForecast retrieved = cache.get(today);
        assertNotNull(retrieved);
        assertEquals("Céu limpo", retrieved.getSummary());
        assertEquals(1, cache.getHits());
        assertEquals(0, cache.getMisses());

        // Segunda chamada: ainda deve estar na cache
        WeatherForecast retrieved2 = cache.get(today);
        assertNotNull(retrieved2);
        assertEquals(2, cache.getHits());
        assertEquals(0, cache.getMisses());
    }

    @Test
    void testCacheMissOnEmpty() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        WeatherForecast result = cache.get(tomorrow);
        assertNull(result);
        assertEquals(0, cache.getHits());
        assertEquals(1, cache.getMisses());
    }
}
