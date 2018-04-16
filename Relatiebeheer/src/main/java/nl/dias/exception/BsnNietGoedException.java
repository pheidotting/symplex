package nl.dias.exception;

public class BsnNietGoedException extends Exception {
    private static final long serialVersionUID = -2557744125174524776L;

    private static final String MESSAGE = "De ingevoerde bsn is niet correct";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
