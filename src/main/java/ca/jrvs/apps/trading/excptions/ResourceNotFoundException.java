package ca.jrvs.apps.trading.excptions;

public class ResourceNotFoundException extends RuntimeException {
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
}
