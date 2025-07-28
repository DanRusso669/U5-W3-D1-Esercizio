package danrusso.U5_W2_Progetto_Settimanale.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id, String type) {
        super(
                type + " with id " + id + " not found."
        );
    }

    public NotFoundException(String message) {
        super(message);
    }
}
