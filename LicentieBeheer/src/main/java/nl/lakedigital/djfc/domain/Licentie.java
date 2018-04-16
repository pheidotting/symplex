package nl.lakedigital.djfc.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorColumn(name = "SOORT", length = 1)
@NamedQuery(name = "Licentie.alleLicenties", query = "select l from Licentie l where kantoor = :kantoor")
public abstract class Licentie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "STARTDATUM")
    @Temporal(TemporalType.DATE)
    private Date startDatum;
    @Column(name = "KANTOOR")
    private Long kantoor;
    @Column(name = "AANTALDAGEN")
    private Integer aantalDagen;

    public Licentie() {
        this.startDatum = new Date();
    }

    public Licentie(Integer aantalDagen) {
        this();
        this.aantalDagen = aantalDagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDatum() {
        return new LocalDate(startDatum);
    }

    public void setStartDatum(LocalDate startDatum) {
        this.startDatum = startDatum.toDate();
    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }

    public Integer getAantalDagen() {
        return aantalDagen;
    }

    public void setAantalDagen(Integer aantalDagen) {
        this.aantalDagen = aantalDagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Licentie)) {
            return false;
        }
        Licentie licentie = (Licentie) o;
        return Objects.equals(getId(), licentie.getId()) && Objects.equals(getStartDatum(), licentie.getStartDatum()) && Objects.equals(getKantoor(), licentie.getKantoor()) && Objects.equals(getAantalDagen(), licentie.getAantalDagen());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getStartDatum(), getKantoor(), getAantalDagen());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("startDatum", startDatum).append("kantoor", kantoor).append("aantalDagen", aantalDagen).toString();
    }
}
