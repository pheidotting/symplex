package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Audited
@Entity
@Table(name = "VERZEKERINGSMAATSCHAPPIJ")
@NamedQueries({//
        @NamedQuery(name = "VerzekeringsMaatschappij.zoekAlles", query = "select v from VerzekeringsMaatschappij v where v.tonen = '1'"),//
        @NamedQuery(name = "VerzekeringsMaatschappij.zoekOpNaam", query = "select v from VerzekeringsMaatschappij v where v.naam = :naam")//
})
public class VerzekeringsMaatschappij implements Serializable, Comparable<VerzekeringsMaatschappij> {
    private static final long serialVersionUID = 1721464750949552535L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAAM", unique = true, nullable = false)
    private String naam;

    @Column(name = "TONEN")
    private boolean tonen;

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

    public boolean isTonen() {
        return tonen;
    }

    public void setTonen(boolean tonen) {
        this.tonen = tonen;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((naam == null) ? 0 : naam.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        VerzekeringsMaatschappij other = (VerzekeringsMaatschappij) obj;
        return new EqualsBuilder().append(id, other.id).append(naam, other.naam).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VerzekeringsMaatschappij [id=");
        builder.append(id);
        builder.append(", naam=");
        builder.append(naam);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(VerzekeringsMaatschappij o) {
        return this.getNaam().compareTo(o.getNaam());
    }
}
