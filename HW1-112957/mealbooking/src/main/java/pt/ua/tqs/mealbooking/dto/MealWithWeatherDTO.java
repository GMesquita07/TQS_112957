package pt.ua.tqs.mealbooking.dto;

import pt.ua.tqs.mealbooking.model.MealType;

public class MealWithWeatherDTO {
    private Long id;
    private String description;
    private MealType type;
    private String date;
    private String weather;

    public MealWithWeatherDTO(Long id, String description, MealType type, String date, String weather) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.date = date;
        this.weather = weather;
    }

    public Long getId() { return id; }
    public String getDescription() { return description; }
    public MealType getType() { return type; }
    public String getDate() { return date; }
    public String getWeather() { return weather; }

    public void setId(Long id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setType(MealType type) { this.type = type; }
    public void setDate(String date) { this.date = date; }
    public void setWeather(String weather) { this.weather = weather; }
}
