package nl.dias.exception;

public class IbanNietGoedException extends Exception {
    private static final long serialVersionUID = -4089298003154023262L;

    private final String message = "Het ingevoerde rekeningnummer is niet correct";

    public String getMessage() {
        return message;
    }

}
