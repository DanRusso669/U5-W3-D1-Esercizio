package danrusso.U5_W2_Progetto_Settimanale.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewReservationDTO(
        @NotNull
        UUID employeeId,
        @NotNull
        UUID journeyId,
        @Size(max = 250, message = "Max lenght for notes is 250 characters.")
        String notes

) {
}
