package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Audited
@Entity
@Table(name = "GROEPBIJLAGE")
@NamedQueries({//
        @NamedQuery(name = "GroepBijlages.allesGroepBijlages", query = "select gb from GroepBijlages gb where gb.bijlages IN (select b from Bijlage b where b.soortEntiteit = :soortEntiteit and b.entiteitId = :entiteitId)")//
})
public class GroepBijlages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAAM")
    private String naam;
    @OneToMany(mappedBy = "groepBijlages", fetch = FetchType.EAGER)
    private List<Bijlage> bijlages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = newArrayList();
        }
        return bijlages;
    }

    public void setBijlages(List<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GroepBijlages)) {
            return false;
        }

        GroepBijlages that = (GroepBijlages) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getNaam(), that.getNaam()).append(getBijlages().size(), that.getBijlages().size()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getNaam()).append(getBijlages().size()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("naam", naam).append("bijlages", bijlages.size()).toString();
    }
}
