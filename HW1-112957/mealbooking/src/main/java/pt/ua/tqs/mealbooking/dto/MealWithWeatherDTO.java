package pt.ua.tqs.mealbooking.dto;

import pt.ua.tqs.mealbooking.model.MealType;

import java.time.LocalDate;

public class MealWithWeatherDTO {

    private Long id;
    private String description;
    private MealType type;
    private LocalDate date;
    private WeatherForecast weatherForecast;

    public MealWithWeatherDTO(Long id, String description, MealType type, LocalDate date, WeatherForecast forecast) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.date = date;
        this.weatherForecast = forecast;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public MealType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }
}
