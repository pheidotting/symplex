package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "R")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQueries({@NamedQuery(name = "Relatie.zoekAllesVoorKantoor", query = "select r from Relatie r where r.kantoor = :kantoor"), //
        @NamedQuery(name = "Relatie.zoekOpEmail", query = "select r from Relatie r where r.identificatie = :emailadres"), //
        @NamedQuery(name = "Relatie.zoekOpBsn", query = "select r from Relatie r where r.bsn = :bsn"), //
        @NamedQuery(name = "Relatie.zoekOpBedrijfsnaam", query = "select r from Relatie r inner join r.bedrijven b where b.naam LIKE :bedrijfsnaam"),//
        @NamedQuery(name = "Relatie.zoekOpGeboortedatum", query = "select g from Gebruiker g where g.geboorteDatum = :geboorteDatum"),//
        @NamedQuery(name = "Relatie.roepnaam", query = "select g from Gebruiker g where g.roepnaam = :roepnaam")//
})
public class Relatie extends Gebruiker implements Serializable, PersistenceObject {
    private static final long serialVersionUID = -1920949633670770763L;

    @Column(name = "ROEPNAAM")
    private String roepnaam;

    @Column(name = "BSN")
    private String bsn;

    @NotAudited
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    @Column(name = "GEBOORTEDATUM")
    @Temporal(TemporalType.DATE)
    private Date geboorteDatum;

    @Column(name = "OVERLIJDENSDATUM")
    @Temporal(TemporalType.DATE)
    private Date overlijdensdatum;

    @Column(name = "GESLACHT", length = 1)
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    @Column(name = "BURGERLIJKESTAAT", length = 1)
    @Enumerated(EnumType.STRING)
    private BurgerlijkeStaat burgerlijkeStaat;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OnderlingeRelatie.class, mappedBy = "relatie")
    @NotAudited
    private Set<OnderlingeRelatie> onderlingeRelaties;

    @NotAudited
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Bedrijf.class, mappedBy = "relatie")
    private Set<Bedrijf> bedrijven;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Hypotheek.class, mappedBy = "relatie", orphanRemoval = true)
    @NotAudited
    private Set<Hypotheek> hypotheken;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = HypotheekPakket.class, mappedBy = "relatie", orphanRemoval = true)
    @NotAudited
    private Set<HypotheekPakket> hypotheekPakketten;

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    public LocalDate getGeboorteDatum() {
        if (geboorteDatum == null) {
            return null;
        }
        return new LocalDate(geboorteDatum);
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum.toDate();
    }

    public LocalDate getOverlijdensdatum() {
        if (overlijdensdatum == null) {
            return null;
        }
        return new LocalDate(overlijdensdatum);
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum.toDate();
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(Geslacht geslacht) {
        this.geslacht = geslacht;
    }

    public BurgerlijkeStaat getBurgerlijkeStaat() {
        return burgerlijkeStaat;
    }

    public void setBurgerlijkeStaat(BurgerlijkeStaat burgerlijkeStaat) {
        this.burgerlijkeStaat = burgerlijkeStaat;
    }

    public Set<OnderlingeRelatie> getOnderlingeRelaties() {
        if (onderlingeRelaties == null) {
            onderlingeRelaties = new HashSet<>();
        }
        return onderlingeRelaties;
    }

    public void setOnderlingeRelaties(Set<OnderlingeRelatie> onderlingeRelaties) {
        this.onderlingeRelaties = onderlingeRelaties;
    }

    public Set<Hypotheek> getHypotheken() {
        if (hypotheken == null) {
            hypotheken = new HashSet<>();
        }
        return hypotheken;
    }

    public void setHypotheken(Set<Hypotheek> hypotheken) {
        this.hypotheken = hypotheken;
    }

    public Set<HypotheekPakket> getHypotheekPakketten() {
        if (hypotheekPakketten == null) {
            hypotheekPakketten = new HashSet<>();
        }
        return hypotheekPakketten;
    }

    public void setHypotheekPakketten(Set<HypotheekPakket> hypotheekPakketten) {
        this.hypotheekPakketten = hypotheekPakketten;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.burgerlijkeStaat).append(this.bsn).append(this.geboorteDatum).append(this.geslacht).append(this.overlijdensdatum).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Relatie)) {
            return false;
        }
        Relatie rhs = (Relatie) object;
        return new EqualsBuilder().appendSuper(super.equals(object)).append(this.burgerlijkeStaat, rhs.burgerlijkeStaat).append(this.bsn, rhs.bsn).append(this.geboorteDatum, rhs.geboorteDatum).append(this.geslacht, rhs.geslacht).append(this.overlijdensdatum, rhs.overlijdensdatum).isEquals();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("\ngeslacht", this.geslacht).append("burgerlijkeStaat", this.burgerlijkeStaat).append("identificatie", this.getIdentificatie()).append("voornaam", this.getVoornaam()).append("id", this.getId()).append("overlijdensdatum", this.overlijdensdatum).append("geboorteDatum", this.geboorteDatum).append("bsn", this.bsn).append("onderlingeRelaties", this.onderlingeRelaties).append("wachtwoordString", this.getWachtwoordString()).append("tussenvoegsel", this.getTussenvoegsel()).append("achternaam", this.getAchternaam()).toString();
    }

    @Override
    public String getName() {
        return this.getIdentificatie();
    }

}
