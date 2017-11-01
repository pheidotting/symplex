package nl.lakedigital.loginsystem.exception;

public class LeegVeldException extends Exception {
	private static final long serialVersionUID = -2850169676198580320L;
	
	public LeegVeldException(String welkeVeld) {
		super("Veld " + welkeVeld + " mag niet leeg zijn.");
	}
}
