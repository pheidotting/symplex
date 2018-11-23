package nl.lakedigital.djfc.commons.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "telefoonnummer")
@XmlType(namespace = "commons.domain")
public class Telefoonnummer extends AbstracteEntiteitMetSoortEnId {
    private String telefoonnummer;
    private String soort;
    private String omschrijving;
    private Long entiteitId;

    public Telefoonnummer() {
    }

    public Telefoonnummer(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie, String telefoonnummer, String soort, String omschrijving, Long entiteitId1) {
        super(soortEntiteit, entiteitId, identificatie);
        this.telefoonnummer = telefoonnummer;
        this.soort = soort;
        this.omschrijving = omschrijving;
        this.entiteitId = entiteitId1;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

}