package nl.dias.domein;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "KANTOOR")
public class Kantoor implements Serializable {
    private static final long serialVersionUID = 3842257675777516787L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAAM")
    private String naam;

    @Column(name = "KVK", length = 8)
    private Long kvk;

    @Column(name = "BTW_NUMMER")
    private String btwNummer;

    @Column(name = "DATUMOPRICHTING")
    @Temporal(TemporalType.DATE)
    private Date datumOprichting;

    @Column(name = "RECHTSVORM", length = 4)
    @Enumerated(EnumType.STRING)
    private Rechtsvorm rechtsvorm;

    @Column(name = "SOORTKANTOOR", length = 3)
    @Enumerated(EnumType.STRING)
    private SoortKantoor soortKantoor;

    @Column(name = "EMAILADRES")
    private String emailadres;

    @Column(name = "AFKORTING", length = 10)
    private String afkorting;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "kantoor", targetEntity = Medewerker.class)
    private List<Medewerker> medewerkers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kantoor", targetEntity = Relatie.class)
    private Set<Relatie> relaties;

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

    public List<Medewerker> getMedewerkers() {
        if (medewerkers == null) {
            medewerkers = new ArrayList<>();
        }
        return medewerkers;
    }

    public void setMedewerkers(List<Medewerker> medewerkers) {
        this.medewerkers = medewerkers;
    }

    public Set<Relatie> getRelaties() {
        if (relaties == null) {
            relaties = new HashSet<>();
        }
        return relaties;
    }

    public void setRelaties(Set<Relatie> relaties) {
        this.relaties = relaties;
    }

    public Long getKvk() {
        return kvk;
    }

    public void setKvk(Long kvk) {
        this.kvk = kvk;
    }

    public String getBtwNummer() {
        return btwNummer;
    }

    public void setBtwNummer(String btwNummer) {
        this.btwNummer = btwNummer;
    }

    public LocalDate getDatumOprichting() {
        return new LocalDate(datumOprichting);
    }

    public void setDatumOprichting(LocalDate datumOprichting) {
        this.datumOprichting = datumOprichting.toDate();
    }

    public Rechtsvorm getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(Rechtsvorm rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public SoortKantoor getSoortKantoor() {
        return soortKantoor;
    }

    public void setSoortKantoor(SoortKantoor soortKantoor) {
        this.soortKantoor = soortKantoor;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getAfkorting() {
        return afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(btwNummer);
        builder.append(datumOprichting);
        builder.append(emailadres);
        builder.append(id);
        builder.append(kvk);
        builder.append(medewerkers);
        builder.append(naam);
        builder.append(rechtsvorm);
        builder.append(soortKantoor);
        builder.append(afkorting);

        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Kantoor other = (Kantoor) obj;
        return new EqualsBuilder().append(btwNummer, other.btwNummer).append(datumOprichting, other.datumOprichting).append(emailadres, other.emailadres).append(id, other.id).append(kvk, other.kvk).append(naam, other.naam).append(rechtsvorm, other.rechtsvorm).append(soortKantoor, other.soortKantoor).append(medewerkers, other.medewerkers).append(relaties, other.relaties).append(afkorting, other.afkorting).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Kantoor [id=");
        builder.append(id);
        builder.append(", naam=");
        builder.append(naam);
        builder.append(", kvk=");
        builder.append(kvk);
        builder.append(", btwNummer=");
        builder.append(btwNummer);
        builder.append(", datumOprichting=");
        builder.append(datumOprichting);
        builder.append(", rechtsvorm=");
        builder.append(rechtsvorm);
        builder.append(", soortKantoor=");
        builder.append(soortKantoor);
        builder.append(", emailadres=");
        builder.append(emailadres);
        builder.append(", medewerkers=");
        builder.append(medewerkers);
        builder.append(", afkorting=");
        builder.append(afkorting);
        builder.append("]");
        return builder.toString();
    }

}
