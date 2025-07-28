package danrusso.U5_W2_Progetto_Settimanale.exceptions;

public class ToManyReservationsException extends RuntimeException {
    public ToManyReservationsException(String message) {
        super(message);
    }
}
