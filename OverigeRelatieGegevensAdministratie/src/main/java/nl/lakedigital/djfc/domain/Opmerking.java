package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Audited
@Entity
@Table(name = "OPMERKING")
@NamedQueries({//
        @NamedQuery(name = "Opmerking.zoekBijEntiteit", query = "select o from Opmerking o where o.soortEntiteit = :soortEntiteit and o.entiteitId = :entiteitId"),//
        @NamedQuery(name = "Opmerking.zoeken", query = "select o from Opmerking o where o.opm like :zoekTerm")//
})
public class Opmerking extends AbstracteEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = -2928569293026238403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TIJD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tijd;
    @Column(name = "MEDEWERKER")
    private Long medewerker;
    @Column(columnDefinition = "varchar(2500)", name = "OPMERKING")
    private String opm;

    public Opmerking() {
        tijd = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTijd() {
        return new LocalDateTime(tijd);
    }

    public void setTijd(LocalDateTime tijd) {
        this.tijd = tijd.toDateTime().toDate();
    }

    public Long getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Long medewerker) {
        this.medewerker = medewerker;
    }

    public String getOpmerking() {
        return opm;
    }

    public void setOpmerking(String opmerking) {
        this.opm = opmerking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Opmerking)) {
            return false;
        }

        Opmerking opmerking1 = (Opmerking) o;

        return new EqualsBuilder().append(getId(), opmerking1.getId()).append(getMedewerker(), opmerking1.getMedewerker()).append(getOpmerking(), opmerking1.getOpmerking()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getMedewerker()).append(getOpmerking()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("tijd", tijd).append("medewerker", medewerker).append("opm", opm).append("opmerking", getOpmerking()).toString();
    }
}
