package nl.lakedigital.djfc.commons.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "opmerking")
@XmlType(namespace = "commons.domain")
public class Opmerking extends AbstracteEntiteitMetSoortEnId {
    private String tijd;
    private String opmerking;
    private String medewerker;
    private Long medewerkerId;

    public Opmerking() {
    }

    public Opmerking(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie, String tijd, String opmerking, String medewerker, Long medewerkerId) {
        super(soortEntiteit, entiteitId, identificatie);
        this.tijd = tijd;
        this.opmerking = opmerking;
        this.medewerker = medewerker;
        this.medewerkerId = medewerkerId;
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
}

