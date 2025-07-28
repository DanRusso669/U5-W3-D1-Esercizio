package danrusso.U5_W2_Progetto_Settimanale.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
