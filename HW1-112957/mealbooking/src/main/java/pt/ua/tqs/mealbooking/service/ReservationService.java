package pt.ua.tqs.mealbooking.service;

import pt.ua.tqs.mealbooking.dto.ReservationRequestDTO;
import pt.ua.tqs.mealbooking.dto.ReservationResponseDTO;
import pt.ua.tqs.mealbooking.model.MealType;
import pt.ua.tqs.mealbooking.model.Reservation;
import pt.ua.tqs.mealbooking.repository.ReservationRepository;
import pt.ua.tqs.mealbooking.repository.RestaurantRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    public ReservationService(ReservationRepository reservationRepository,
                               RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public ReservationResponseDTO createReservation(ReservationRequestDTO request) {
        MealType type = request.getType();

        LocalDate date = request.getDate(); // ❗ Corrigido aqui — remover o parse

        if (reservationRepository.existsByRestaurantIdAndDateAndTypeAndCancelledFalse(
                request.getRestaurantId(), date, type)) {
            throw new IllegalStateException("Reserva duplicada para o mesmo dia e tipo");
        }

        Reservation reservation = new Reservation();
        reservation.setRestaurantId(request.getRestaurantId());
        reservation.setMealId(request.getMealId());
        reservation.setDate(date);
        reservation.setType(type);
        reservation.setToken(UUID.randomUUID().toString());
        reservation.setCancelled(false);
        reservation.setCheckedIn(false);

        reservationRepository.save(reservation);

        String restaurantName = restaurantRepository.findById(reservation.getRestaurantId())
                .map(r -> r.getName())
                .orElse("Desconhecido");

        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getToken(),
                reservation.getType(),
                reservation.getRestaurantId(),
                reservation.getMealId(),
                reservation.getDate().toString(),
                reservation.isCancelled(),
                reservation.isCheckedIn(),
                restaurantName
        );
    }

    public ReservationResponseDTO getReservationByToken(String token) {
        Reservation reservation = reservationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        String restaurantName = restaurantRepository.findById(reservation.getRestaurantId())
                .map(r -> r.getName())
                .orElse("Desconhecido");

        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getToken(),
                reservation.getType(),
                reservation.getRestaurantId(),
                reservation.getMealId(),
                reservation.getDate().toString(),
                reservation.isCancelled(),
                reservation.isCheckedIn(),
                restaurantName
        );
    }

    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(res -> new ReservationResponseDTO(
                        res.getId(),
                        res.getToken(),
                        res.getType(),
                        res.getRestaurantId(),
                        res.getMealId(),
                        res.getDate().toString(),
                        res.isCancelled(),
                        res.isCheckedIn(),
                        restaurantRepository.findById(res.getRestaurantId())
                                .map(r -> r.getName())
                                .orElse("Desconhecido")
                ))
                .collect(Collectors.toList());
    }

    public void checkInReservation(String token) {
        Reservation reservation = reservationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (reservation.isCancelled()) {
            throw new IllegalStateException("A reserva foi cancelada");
        }

        if (reservation.isCheckedIn()) {
            throw new IllegalStateException("Já foi feito check-in nesta reserva");
        }

        reservation.setCheckedIn(true);
        reservationRepository.save(reservation);
    }

    public void cancelReservation(String token) {
        Reservation reservation = reservationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if (!reservation.isCancelled()) {
            reservation.setCancelled(true);
            reservationRepository.save(reservation);
        }
    }

}
