package pt.ua.tqs.mealbooking.repository;

import pt.ua.tqs.mealbooking.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
