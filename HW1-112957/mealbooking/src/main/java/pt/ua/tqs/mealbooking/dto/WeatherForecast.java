package pt.ua.tqs.mealbooking.dto;

import java.time.LocalDate;

public class WeatherForecast {
    private LocalDate date;
    private String summary;
    private double temperature;

    public WeatherForecast(LocalDate date, String summary, double temperature) {
        this.date = date;
        this.summary = summary;
        this.temperature = temperature;
    }

    public LocalDate getDate() { return date; }
    public String getSummary() { return summary; }
    public double getTemperature() { return temperature; }
}