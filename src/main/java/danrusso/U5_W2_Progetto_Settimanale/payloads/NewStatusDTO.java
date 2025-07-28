package danrusso.U5_W2_Progetto_Settimanale.payloads;

import jakarta.validation.constraints.NotBlank;

public record NewStatusDTO(
        @NotBlank(message = "Status is mandatory.")
        String status) {
}
