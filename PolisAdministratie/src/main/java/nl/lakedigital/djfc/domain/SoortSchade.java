package nl.lakedigital.djfc.domain;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "SOORTSCHADE")
@NamedQueries({@NamedQuery(name = "SoortSchade.alles", query = "select s from SoortSchade s where s.ingebruik = '1'"), @NamedQuery(name = "SoortSchade.zoekOpOmschrijving", query = "select s from SoortSchade s where s.omschrijving like :omschrijving and s.ingebruik = '1'")})
public class SoortSchade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OMSCHRIJVING", length = 250, nullable = false)
    private String omschrijving;

    @Column(name = "INGEBRUIK")
    private boolean ingebruik;

    public SoortSchade() {
        ingebruik = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public boolean isIngebruik() {
        return ingebruik;
    }

    public void setIngebruik(boolean ingebruik) {
        this.ingebruik = ingebruik;
    }

    @Override
    public String toString() {
        return omschrijving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SoortSchade)) {
            return false;
        }

        SoortSchade that = (SoortSchade) o;

        return new EqualsBuilder().append(isIngebruik(), that.isIngebruik()).append(getId(), that.getId()).append(getOmschrijving(), that.getOmschrijving()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getOmschrijving()).append(isIngebruik()).toHashCode();
    }
}
