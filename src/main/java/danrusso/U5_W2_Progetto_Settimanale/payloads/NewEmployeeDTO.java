package danrusso.U5_W2_Progetto_Settimanale.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(
        @NotBlank
        @Size(min = 3, max = 15, message = "Surname's lenght must be between 3 and 15.")
        String username,
        @NotBlank(message = "Name is mandatory.")
        @Size(min = 3, max = 25, message = "Surname's lenght must be between 3 and 25.")
        String name,
        @NotBlank(message = "Surname is mandatory.")
        @Size(min = 3, max = 25, message = "Surname's lenght must be between 3 and 25.")
        String surname,
        @NotBlank(message = "Email is mandatory.")
        @Email(message = "Email format not valid.")
        String email
) {
}
