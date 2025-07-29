package danrusso.U5_W2_Progetto_Settimanale.controllers;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.entities.Reservation;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.ValidationException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewReservationDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewUserReservationDTO;
import danrusso.U5_W2_Progetto_Settimanale.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Reservation> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "date") String sortBy) {
        return this.reservationService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createJourney(@RequestBody @Validated NewUserReservationDTO payload, BindingResult validationResults, @AuthenticationPrincipal Employee currentAuthEmployee) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }

        return this.reservationService.saveReservation(payload, currentAuthEmployee);
    }

    @PutMapping("/{reservationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Reservation findByIdAndUpdate(@PathVariable UUID reservationId, @RequestBody @Validated NewReservationDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.reservationService.findByIdAndUpdate(reservationId, payload);
    }

    @GetMapping("/{reservationId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Reservation findById(@PathVariable UUID reservationId) {
        return this.reservationService.findById(reservationId);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID reservationId) {
        this.reservationService.findByIdAndDelete(reservationId);
    }
}
