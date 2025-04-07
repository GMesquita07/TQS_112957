package pt.ua.tqs.mealbooking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ua.tqs.mealbooking.dto.ReservationRequestDTO;
import pt.ua.tqs.mealbooking.dto.ReservationResponseDTO;
import pt.ua.tqs.mealbooking.model.Meal;
import pt.ua.tqs.mealbooking.model.MealType;
import pt.ua.tqs.mealbooking.model.Restaurant;
import pt.ua.tqs.mealbooking.repository.MealRepository;
import pt.ua.tqs.mealbooking.repository.ReservationRepository;
import pt.ua.tqs.mealbooking.repository.RestaurantRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    private Restaurant restaurant;
    private Meal meal;

    @BeforeEach
    void setup() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();

        restaurant = restaurantRepository.save(new Restaurant("Cantina Central", "Aveiro"));
        meal = mealRepository.save(new Meal(LocalDate.now().plusDays(1), MealType.ALMOCO, "Arroz de pato", restaurant)
);

    }

    @Test
    void testCreateReservation() {
        var request = new ReservationRequestDTO(restaurant.getId(), meal.getId(), meal.getDate(), meal.getType());
        var response = reservationService.createReservation(request);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals(meal.getDate().toString(), response.getDate());
        assertEquals(meal.getType(), response.getType());
    }

    @Test
    void testDuplicateReservationFails() {
        var request = new ReservationRequestDTO(restaurant.getId(), meal.getId(), meal.getDate(), meal.getType());

        reservationService.createReservation(request);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.createReservation(request);
        });
    }
}
