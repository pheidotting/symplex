package nl.lakedigital.djfc.commons.json;

import java.util.List;

public class JsonOpmerkingenModel {
    private List<JsonOpmerking> opmerkingen;
    private String nieuweOpmerking;
    private String schade;
    private String hypotheek;
    private String polis;
    private String relatie;
    private String bedrijf;
    private String aangifte;

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getNieuweOpmerking() {
        return nieuweOpmerking;
    }

    public void setNieuweOpmerking(String nieuweOpmerking) {
        this.nieuweOpmerking = nieuweOpmerking;
    }

    public String getSchade() {
        return schade;
    }

    public void setSchade(String schade) {
        this.schade = schade;
    }

    public String getHypotheek() {
        return hypotheek;
    }

    public void setHypotheek(String hypotheek) {
        this.hypotheek = hypotheek;
    }

    public String getPolis() {
        return polis;
    }

    public void setPolis(String polis) {
        this.polis = polis;
    }

    public String getRelatie() {
        return relatie;
    }

    public void setRelatie(String relatie) {
        this.relatie = relatie;
    }

    public String getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getAangifte() {
        return aangifte;
    }

    public void setAangifte(String aangifte) {
        this.aangifte = aangifte;
    }
}
