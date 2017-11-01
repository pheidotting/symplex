package nl.dias.exception;

public class NietIngelogdException extends Exception {
    private static final long serialVersionUID = 5395608101463796626L;

    private final String message = "Helaas, je bent niet ingelogd, of je sessie is verlopen.";

    public String getMessage() {
        return message;
    }
}
