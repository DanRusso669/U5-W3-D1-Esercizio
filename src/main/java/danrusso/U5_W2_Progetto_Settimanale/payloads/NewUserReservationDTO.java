package danrusso.U5_W2_Progetto_Settimanale.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewUserReservationDTO(
        @NotNull
        UUID journeyId,
        @NotEmpty
        String notes
) {
}
