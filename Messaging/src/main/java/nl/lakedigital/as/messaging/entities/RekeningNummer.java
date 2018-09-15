package nl.lakedigital.as.messaging.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "rekeningnummer")
@XmlType(namespace = "entities")
public class RekeningNummer {
    private String identificatie;
    private String bic;
    private String rekeningnummer;

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
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
