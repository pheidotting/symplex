package nl.lakedigital.djfc.client;

public class LeesFoutException extends RuntimeException {
    public LeesFoutException(String message) {
        super(message);
    }

    public LeesFoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
