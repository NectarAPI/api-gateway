package ke.co.nectar.api.controllers.exceptions;

public class IncompatibleUserException
    extends Exception {
    public IncompatibleUserException(String message) {
        super(message);
    }
}
