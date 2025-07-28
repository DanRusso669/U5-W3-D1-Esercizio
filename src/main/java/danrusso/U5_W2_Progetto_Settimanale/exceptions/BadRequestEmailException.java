package danrusso.U5_W2_Progetto_Settimanale.exceptions;

public class BadRequestEmailException extends RuntimeException {
    public BadRequestEmailException(String email) {
        super("Email " + email + " already been used.");
    }
}
