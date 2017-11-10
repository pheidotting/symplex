package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Audited
@Entity
@Table(name = "REKENINGNUMMER")
@NamedQueries({//
        @NamedQuery(name = "RekeningNummer.zoekBijEntiteit", query = "select r from RekeningNummer r where r.soortEntiteit = :soortEntiteit and r.entiteitId = :entiteitId"),//
        @NamedQuery(name = "RekeningNummer.zoeken", query = "select r from RekeningNummer r where r.rekNr like :zoekTerm")//
})
public class RekeningNummer extends AbstracteEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = 6164849876034232194L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BIC")
    private String bic;
    @Column(name = "REKENINGNUMMER")
    private String rekNr;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getRekeningnummer() {
        return rekNr;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekNr = rekeningnummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RekeningNummer)) {
            return false;
        }

        RekeningNummer that = (RekeningNummer) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getBic(), that.getBic()).append(getRekeningnummer(), that.getRekeningnummer()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getBic()).append(getRekeningnummer()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("bic", bic).append("rekeningnummer", rekNr).toString();
    }
}
