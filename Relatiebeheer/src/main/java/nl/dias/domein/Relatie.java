package nl.dias.domein;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Audited
@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "R")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQueries({@NamedQuery(name = "Relatie.zoekAllesVoorKantoor", query = "select r from Relatie r where r.kantoor = :kantoor"), //
        @NamedQuery(name = "Relatie.zoekOpEmail", query = "select r from Relatie r where r.identificatie = :emailadres"), //
        @NamedQuery(name = "Relatie.zoekOpGeboortedatum", query = "select g from Relatie g where g.geboorteDatum = :geboorteDatum"),//
        @NamedQuery(name = "Relatie.roepnaam", query = "select g from Relatie g where g.roepnaam LIKE :roepnaam")//
})
public class Relatie extends Gebruiker implements Serializable {
    private static final long serialVersionUID = -1920949633670770763L;

    @Column(name = "ROEPNAAM")
    private String roepnaam;

    @NotAudited
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false, targetEntity = Kantoor.class)
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

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
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

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.burgerlijkeStaat).append(this.geboorteDatum).append(this.geslacht).append(this.overlijdensdatum).toHashCode();
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
        return new EqualsBuilder().appendSuper(super.equals(object)).append(this.burgerlijkeStaat, rhs.burgerlijkeStaat).append(this.geboorteDatum, rhs.geboorteDatum).append(this.geslacht, rhs.geslacht).append(this.overlijdensdatum, rhs.overlijdensdatum).isEquals();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("\ngeslacht", this.geslacht).append("burgerlijkeStaat", this.burgerlijkeStaat).append("identificatie", this.getIdentificatie()).append("voornaam", this.getVoornaam()).append("id", this.getId()).append("overlijdensdatum", this.overlijdensdatum).append("geboorteDatum", this.geboorteDatum).append("wachtwoordString", this.getWachtwoordString()).append("tussenvoegsel", this.getTussenvoegsel()).append("achternaam", this.getAchternaam()).toString();
    }

    public String getName() {
        return this.getIdentificatie();
    }

}
