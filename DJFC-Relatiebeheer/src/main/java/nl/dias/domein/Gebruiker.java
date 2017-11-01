package nl.dias.domein;

import nl.lakedigital.domein.Onderwerp;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorColumn(name = "SOORT", length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQueries({//
        @NamedQuery(name = "Gebruiker.zoekOpEmail", query = "select g from Gebruiker g where g.emailadres = :emailadres"), //
        @NamedQuery(name = "Gebruiker.zoekOpIdentificatie", query = "select g from Gebruiker g where g.identificatie = :identificatie"), //
        @NamedQuery(name = "Gebruiker.zoekOpSessieEnIpAdres", query = "select distinct g from Gebruiker g join g.sessies as s where s.sessie = :sessie and s.ipadres = :ipadres"), //
        @NamedQuery(name = "Gebruiker.zoekOpCookieCode", query = "select distinct g from Gebruiker g join g.sessies as s where s.cookieCode = :cookieCode"), //
        @NamedQuery(name = "Gebruiker.zoekOpNaam", query = "select g from Relatie g where g.voornaam like :naam or g.achternaam like :naam"),//
        @NamedQuery(name = "Gebruiker.alles", query = "select g from Gebruiker g"),//
        @NamedQuery(name = "Gebruiker.zoekOpTussenVoegsel", query = "select g from Gebruiker g where g.tussenvoegsel = :tussenvoegsel"),//
})
public abstract class Gebruiker extends Onderwerp implements PersistenceObject, Principal {
    private static final long serialVersionUID = -643848502264838675L;

    @Column(name = "VOORNAAM")
    private String voornaam;
    @Column(name = "TUSSENVOEGSEL")
    private String tussenvoegsel;
    @Column(name = "ACHTERNAAM")
    private String achternaam;
    @Column(name = "EMAILADRES")
    private String emailadres;
    @NotAudited
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gebruiker", orphanRemoval = true, targetEntity = Sessie.class)
    private Set<Sessie> sessies;

    @Transient
    private String wachtwoordString;

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public Set<Sessie> getSessies() {
        if (sessies == null) {
            sessies = new HashSet<>();
        }
        return sessies;
    }

    public void setSessies(Set<Sessie> sessies) {
        this.sessies = sessies;
    }

    public String getWachtwoordString() {
        return wachtwoordString;
    }

    public void setWachtwoordString(String wachtwoordString) {
        this.wachtwoordString = wachtwoordString;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("wachtwoordString", this.wachtwoordString).append("achternaam", this.achternaam).append("tussenvoegsel", this.tussenvoegsel).append("voornaam", this.voornaam).append("emailadres", emailadres).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Gebruiker)) {
            return false;
        }

        Gebruiker gebruiker = (Gebruiker) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getVoornaam(), gebruiker.getVoornaam()).append(getTussenvoegsel(), gebruiker.getTussenvoegsel()).append(getAchternaam(), gebruiker.getAchternaam()).append(getEmailadres(), gebruiker.getEmailadres()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getVoornaam()).append(getTussenvoegsel()).append(getAchternaam()).append(getEmailadres()).toHashCode();
    }

    public String getNaam() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVoornaam());
        if (getTussenvoegsel() != null) {
            sb.append(" ").append(getTussenvoegsel());
        }
        sb.append(" ").append(getAchternaam());
        return sb.toString();
    }
}
