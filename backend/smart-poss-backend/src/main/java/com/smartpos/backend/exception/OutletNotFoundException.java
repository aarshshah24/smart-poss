package  com.smartpos.backend.exception;

public class OutletNotFoundException extends RuntimeException {
    public OutletNotFoundException(String message) {
        super(message);
    }
}
