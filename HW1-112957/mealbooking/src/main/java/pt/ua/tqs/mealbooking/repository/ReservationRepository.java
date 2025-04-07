package pt.ua.tqs.mealbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ua.tqs.mealbooking.model.Reservation;
import pt.ua.tqs.mealbooking.model.MealType;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByRestaurantIdAndDateAndTypeAndCancelledFalse(Long restaurantId, LocalDate date, MealType type);

    Optional<Reservation> findByToken(String token);
}
