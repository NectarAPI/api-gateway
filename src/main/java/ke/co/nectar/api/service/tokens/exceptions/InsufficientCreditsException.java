package ke.co.nectar.api.service.tokens.exceptions;

public class InsufficientCreditsException extends Exception {

    public InsufficientCreditsException(String message) {
        super(message);
    }
}
