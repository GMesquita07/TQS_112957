package pt.ua.tqs.mealbooking.controller;

import pt.ua.tqs.mealbooking.dto.ReservationRequestDTO;
import pt.ua.tqs.mealbooking.dto.ReservationResponseDTO;
import pt.ua.tqs.mealbooking.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDTO request) {
        try {
            ReservationResponseDTO response = reservationService.createReservation(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Restaurante ou tipo de refeição inválido");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("Reserva duplicada para o mesmo dia e tipo");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar reserva: " + e.getMessage());
        }
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getReservation(@PathVariable String token) {
        try {
            ReservationResponseDTO response = reservationService.getReservationByToken(token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestParam String token) {
        try {
            reservationService.checkInReservation(token);
            return ResponseEntity.ok("Check-in realizado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{token}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable String token) {
        try {
            reservationService.cancelReservation(token);
            return ResponseEntity.ok("Reserva cancelada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cancelar reserva");
        }
    }

    @GetMapping
    public List<ReservationResponseDTO> getReservationsByTokens(@RequestParam List<String> tokens) {
        return tokens.stream()
                .map(reservationService::getReservationByToken)
                .filter(res -> res != null && !res.isCancelled()) // ignora reservas canceladas
                .toList();
    }


}
