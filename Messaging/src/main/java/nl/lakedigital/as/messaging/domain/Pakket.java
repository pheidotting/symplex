package nl.lakedigital.as.messaging.domain;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "pakket")
public class Pakket {
    private Long id;
    private String identificatie;
    private String polisNummer;
    private String soortEntiteit;
    private Long entiteitId;
    private Long maatschappij;
    private String parentIdentificatie;
    private List<Polis> polissen;
    private List<Opmerking> opmerkingen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public Long getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(Long maatschappij) {
        this.maatschappij = maatschappij;
    }

    public String getParentIdentificatie() {
        return parentIdentificatie;
    }

    public void setParentIdentificatie(String parentIdentificatie) {
        this.parentIdentificatie = parentIdentificatie;
    }

    public List<Polis> getPolissen() {
        if (polissen == null) {
            polissen = new ArrayList<>();
        }
        return polissen;
    }

    public void setPolissen(List<Polis> polissen) {
        this.polissen = polissen;
    }

    public List<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
