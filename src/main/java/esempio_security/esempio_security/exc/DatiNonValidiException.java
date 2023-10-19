package esempio_security.esempio_security.exc;

public class DatiNonValidiException extends RuntimeException {
    public DatiNonValidiException(String message) {
        super(message);
    }
}
