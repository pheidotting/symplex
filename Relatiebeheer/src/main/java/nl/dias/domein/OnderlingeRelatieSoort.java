package nl.dias.domein;

import java.util.EnumMap;

public enum OnderlingeRelatieSoort {
	O("Ouder"), K("Kind"), W("Werknemer"), WG("Werkgever");

	private String omschrijving;

	private OnderlingeRelatieSoort(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}


	public static OnderlingeRelatieSoort getTegenGesteld(OnderlingeRelatieSoort soort) {
		EnumMap<OnderlingeRelatieSoort, OnderlingeRelatieSoort> soorten = new EnumMap<>(OnderlingeRelatieSoort.class);
		soorten.put(O, K);
		soorten.put(K, O);
		soorten.put(W, WG);
		soorten.put(WG, W);

		return soorten.get(soort);
	}
}