package nl.lakedigital.djfc.commons.json;

public class JsonKoppelenOnderlingeRelatie {
    private Long relatie;
    private Long relatieMet;
    private String soortRelatie;

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Long relatie) {
        this.relatie = relatie;
    }

    public Long getRelatieMet() {
        return relatieMet;
    }

    public void setRelatieMet(Long relatieMet) {
        this.relatieMet = relatieMet;
    }

    public String getSoortRelatie() {
        return soortRelatie;
    }

    public void setSoortRelatie(String soortRelatie) {
        this.soortRelatie = soortRelatie;
    }
}
