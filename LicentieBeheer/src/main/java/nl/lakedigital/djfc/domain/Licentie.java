package nl.lakedigital.djfc.domain;


import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorColumn(name = "SOORT", length = 1)
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
}
