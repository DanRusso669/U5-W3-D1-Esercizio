package danrusso.U5_W2_Progetto_Settimanale.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
