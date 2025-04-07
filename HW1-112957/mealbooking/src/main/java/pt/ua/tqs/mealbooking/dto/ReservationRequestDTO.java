package pt.ua.tqs.mealbooking.dto;

import pt.ua.tqs.mealbooking.model.MealType;

public class ReservationRequestDTO {
    private Long restaurantId;
    private Long mealId;
    private String date;
    private MealType type;  // Alterado de String para MealType

    // Getters e Setters

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MealType getType() {
        return type;
    }

    public void setType(MealType type) {
        this.type = type;
    }
}
