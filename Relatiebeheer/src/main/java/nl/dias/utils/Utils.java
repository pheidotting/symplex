package nl.dias.utils;

public final class Utils {

	private Utils() {
	}

	public static String beginHoofdletter(String tekst) {
		String deelEen = tekst.substring(0, 1);
		String deelTwee = tekst.substring(1);

		return deelEen.toUpperCase() + deelTwee;
	}

	public static String getUploadPad() {
		return System.getProperty("uploadPad");
	}

	public static String getOmgeving() {
		return System.getProperty("omgeving");
	}
}
