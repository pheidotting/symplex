package nl.lakedigital.loginsystem.exception;

public class NietGevondenException extends Exception {
	private static final long serialVersionUID = -8410981136577598166L;
	
	public NietGevondenException(String wie) {
		super(wie + " werd niet gevonden.");
	}
}
