package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "IDENTIFICATIE")
@NamedQueries(//
        {//
                @NamedQuery(name = "Identificatie.zoek", query = "select i from Identificatie i where i.soortEntiteit = :soortEntiteit and i.entiteitId = :entiteitId"),//
                @NamedQuery(name = "Identificatie.zoekOpIdentificatieCode", query = "select i from Identificatie i where i.identificatie = :identificatie")//
        }//
)
public class Identificatie implements Serializable {
    private static final long serialVersionUID = 1011438129295546984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IDENTIFICATIE")
    private String identificatie;
    @Column(length = 50, name = "SOORTENTITEIT")
    private String soortEntiteit;
    @Column(name = "ENTITEITID")
    private Long entiteitId;

    public Identificatie() {
    }

    public Identificatie(String soortEntiteit, Long entiteitId) {
        this.soortEntiteit = soortEntiteit;
        this.entiteitId = entiteitId;
    }

    public void nieuweIdentificatieCode() {
        identificatie = UUID.randomUUID().toString();
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("identificatie", identificatie).append("soortEntiteit", soortEntiteit).append("entiteitId", entiteitId).toString();
    }
}
