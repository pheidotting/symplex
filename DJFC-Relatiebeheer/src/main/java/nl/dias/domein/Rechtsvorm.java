package nl.dias.domein;

public enum Rechtsvorm {
	EENM("Eenmanszaak"),
	VOF("V.O.F."),
	BV("BV");
	
	private String omschrijving;
	
	private Rechtsvorm(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

}
