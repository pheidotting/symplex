package nl.dias.exception;

public class EmailAdresFoutiefException extends Exception {
    private static final long serialVersionUID = -2557744125174524776L;

    private final String message = "Het ingevoerde e-mailadres is onjuist.";

    public String getMessage() {
        return message;
    }
}
