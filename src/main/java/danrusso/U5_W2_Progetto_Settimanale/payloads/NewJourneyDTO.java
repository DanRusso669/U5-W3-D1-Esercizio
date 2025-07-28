package danrusso.U5_W2_Progetto_Settimanale.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewJourneyDTO(
        @NotBlank(message = "Destination is mandatory.")
        String destination,
        @NotNull(message = "Date is mandatory.")
        LocalDate date
//        @NotBlank(message = "Status is mandatory.")
//        String status
) {
}
