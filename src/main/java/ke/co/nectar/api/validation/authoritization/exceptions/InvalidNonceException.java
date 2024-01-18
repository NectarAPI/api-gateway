package ke.co.nectar.api.validation.authoritization.exceptions;

public class InvalidNonceException extends Exception {

    public InvalidNonceException() {
        super();
    }

    public InvalidNonceException(String message) {
        super(message);
    }
}
