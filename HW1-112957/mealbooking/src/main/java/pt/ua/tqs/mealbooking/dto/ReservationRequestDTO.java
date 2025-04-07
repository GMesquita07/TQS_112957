package pt.ua.tqs.mealbooking.dto;

import pt.ua.tqs.mealbooking.model.MealType;

import java.time.LocalDate;

public class ReservationRequestDTO {
    private Long restaurantId;
    private Long mealId;
    private LocalDate date;
    private MealType type;

    public ReservationRequestDTO() {
        // Construtor vazio necessário para (des)serialização JSON
    }

    public ReservationRequestDTO(Long restaurantId, Long mealId, LocalDate date, MealType type) {
        this.restaurantId = restaurantId;
        this.mealId = mealId;
        this.date = date;
        this.type = type;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MealType getType() {
        return type;
    }

    public void setType(MealType type) {
        this.type = type;
    }
}
