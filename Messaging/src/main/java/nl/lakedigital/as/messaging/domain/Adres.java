package nl.lakedigital.as.messaging.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "adres")
public class Adres extends AbstracteEntiteitMetSoortEnId {
    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private String soortAdres;

    public Adres() {
    }

    public Adres(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie, String straat, Long huisnummer, String toevoeging, String postcode, String plaats, String soortAdres) {
        super(soortEntiteit, entiteitId, identificatie);
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.toevoeging = toevoeging;
        this.postcode = postcode;
        this.plaats = plaats;
        this.soortAdres = soortAdres;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Long getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Long huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getSoortAdres() {
        return soortAdres;
    }

    public void setSoortAdres(String soortAdres) {
        this.soortAdres = soortAdres;
    }
}
