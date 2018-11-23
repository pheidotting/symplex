package nl.lakedigital.djfc.commons.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "rekeningnummer")
@XmlType(namespace = "commons.domain")
public class RekeningNummer extends AbstracteEntiteitMetSoortEnId {
    private String bic;
    private String rekeningnummer;

    public RekeningNummer() {
    }

    public RekeningNummer(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie, String bic, String rekeningnummer) {
        super(soortEntiteit, entiteitId, identificatie);
        this.bic = bic;
        this.rekeningnummer = rekeningnummer;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }
}
