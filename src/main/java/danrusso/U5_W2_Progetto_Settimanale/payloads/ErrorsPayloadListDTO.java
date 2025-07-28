package danrusso.U5_W2_Progetto_Settimanale.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsPayloadListDTO(String message, LocalDateTime timestamp, List<String> errorsList) {
}
