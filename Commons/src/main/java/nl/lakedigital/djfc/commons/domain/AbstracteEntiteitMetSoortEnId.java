package nl.lakedigital.djfc.commons.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({//
        Adres.class,//
        Opmerking.class,//
        RekeningNummer.class,//
        Telefoonnummer.class})
public abstract class AbstracteEntiteitMetSoortEnId {
    private SoortEntiteit soortEntiteit;
    private Long entiteitId;
    private String identificatie;

    public AbstracteEntiteitMetSoortEnId() {
    }

    public AbstracteEntiteitMetSoortEnId(SoortEntiteit soortEntiteit, Long entiteitId, String identificatie) {
        this.soortEntiteit = soortEntiteit;
        this.entiteitId = entiteitId;
        this.identificatie = identificatie;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }
}
