package nl.lakedigital.as.messaging.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opmerking")
public class Opmerking extends AbstracteEntiteitMetSoortEnId {
    private Long medewerker;
    private String tijdstip;
    private String tekst;

    public Opmerking() {
    }

    public Opmerking(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie, Long medewerker, String tijdstip, String tekst) {
        super(soortEntiteit, entiteitId, identificatie);
        this.medewerker = medewerker;
        this.tijdstip = tijdstip;
        this.tekst = tekst;
    }

    public Long getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Long medewerker) {
        this.medewerker = medewerker;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

}
