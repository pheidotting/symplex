package nl.lakedigital.loginsystem.exception;

public class OnjuistWachtwoordException extends Exception {
    private static final long serialVersionUID = 9184576660264307892L;

    public OnjuistWachtwoordException() {
        super("Het ingevoerde wachtwoord is onjuist");
    }
}
