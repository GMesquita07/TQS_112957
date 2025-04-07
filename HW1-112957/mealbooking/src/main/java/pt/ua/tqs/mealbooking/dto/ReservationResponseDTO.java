package pt.ua.tqs.mealbooking.dto;

import pt.ua.tqs.mealbooking.model.MealType;

public class ReservationResponseDTO {
    private Long id;
    private String token;
    private MealType type;
    private Long restaurantId;
    private Long mealId;
    private String date;
    private boolean cancelled;
    private boolean checkedIn; // <-- novo campo
    private String restaurantName;

    public ReservationResponseDTO() {}

    public ReservationResponseDTO(Long id, String token, MealType type, Long restaurantId,
                                   Long mealId, String date, boolean cancelled, boolean checkedIn, String restaurantName) {
        this.id = id;
        this.token = token;
        this.type = type;
        this.restaurantId = restaurantId;
        this.mealId = mealId;
        this.date = date;
        this.cancelled = cancelled;
        this.checkedIn = checkedIn;
        this.restaurantName = restaurantName;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public MealType getType() { return type; }
    public void setType(MealType type) { this.type = type; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public Long getMealId() { return mealId; }
    public void setMealId(Long mealId) { this.mealId = mealId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    public boolean isCheckedIn() { return checkedIn; }
    public void setCheckedIn(boolean checkedIn) { this.checkedIn = checkedIn; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
}
