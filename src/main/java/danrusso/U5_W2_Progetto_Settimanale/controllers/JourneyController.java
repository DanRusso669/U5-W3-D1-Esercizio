package danrusso.U5_W2_Progetto_Settimanale.controllers;

import danrusso.U5_W2_Progetto_Settimanale.entities.Journey;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.ValidationException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewJourneyDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewStatusDTO;
import danrusso.U5_W2_Progetto_Settimanale.services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/journeys")
public class JourneyController {
    @Autowired
    private JourneyService journeyService;

    @GetMapping
    public Page<Journey> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "destination") String sortBy) {
        return this.journeyService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Journey createJourney(@RequestBody @Validated NewJourneyDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }

        return this.journeyService.saveJourney(payload);
    }

    @GetMapping("/{journeyId}")
    public Journey findById(@PathVariable UUID journeyId) {
        return this.journeyService.findById(journeyId);
    }

    @PutMapping("/{journeyId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Journey findByIdAndUpdate(@PathVariable UUID journeyId, @RequestBody @Validated NewJourneyDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.journeyService.findByIdAndUpdate(journeyId, payload);
    }

    @DeleteMapping("/{journeyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID journeyId) {
        this.journeyService.findByIdAndDelete(journeyId);
    }

    @PatchMapping("/{journeyId}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Journey findByIdAndChangeStatus(@PathVariable UUID journeyId, @RequestBody @Validated NewStatusDTO newStatus, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.journeyService.findByIdAndChangeStatus(journeyId, newStatus);
    }
}
