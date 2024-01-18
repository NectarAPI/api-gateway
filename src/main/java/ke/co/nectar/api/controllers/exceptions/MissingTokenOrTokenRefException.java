package ke.co.nectar.api.controllers.exceptions;

public class MissingTokenOrTokenRefException extends Exception {

    public MissingTokenOrTokenRefException(String message) {
        super(message);
    }
}
