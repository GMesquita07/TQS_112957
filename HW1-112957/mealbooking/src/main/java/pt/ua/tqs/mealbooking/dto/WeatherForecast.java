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

    public LocalDate getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
