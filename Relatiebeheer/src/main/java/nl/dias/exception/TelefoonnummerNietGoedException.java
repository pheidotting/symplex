package nl.dias.exception;

public class TelefoonnummerNietGoedException extends Exception {
    private static final long serialVersionUID = 1010323122945085408L;

    private final String message = "Het ingevoerde telefoonnummer is niet correct";

    @Override
    public String getMessage() {
        return message;
    }
}
