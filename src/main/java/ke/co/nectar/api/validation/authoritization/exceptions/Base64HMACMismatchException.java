package ke.co.nectar.api.validation.authoritization.exceptions;

public class Base64HMACMismatchException extends Exception {

    public Base64HMACMismatchException() {
        super();
    }

    public Base64HMACMismatchException(String message) {
        super(message);
    }
}
