package danrusso.U5_W2_Progetto_Settimanale.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
