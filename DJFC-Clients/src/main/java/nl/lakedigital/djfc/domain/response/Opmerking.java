package nl.lakedigital.djfc.domain.response;

public class Opmerking implements Comparable<Opmerking> {
    private String identificatie;
    private String tijd;
    private String opmerking;
    private String medewerker;
    private Long medewerkerId;

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public String getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(String medewerker) {
        this.medewerker = medewerker;
    }

    public Long getMedewerkerId() {
        return medewerkerId;
    }

    public void setMedewerkerId(Long medewerkerId) {
        this.medewerkerId = medewerkerId;
    }

    @Override
    public int compareTo(Opmerking o) {
        return tijd.compareTo(o.tijd) * -1;
    }
}
