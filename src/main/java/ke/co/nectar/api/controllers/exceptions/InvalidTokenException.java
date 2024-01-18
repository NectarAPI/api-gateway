package ke.co.nectar.api.controllers.exceptions;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super(message);
    }
}
