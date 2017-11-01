package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "M")
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM")) })
//@NamedQueries({ @NamedQuery(name = "Medewerker.zoekOpEmail", query = "select m from Medewerker m where m.identificatie = :emailadres") })
public class Medewerker extends Gebruiker implements Serializable, PersistenceObject {
    private static final long serialVersionUID = -4313251874716582151L;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    @Column(name = "OAUTHTODOIST")
    private String oAuthCodeTodoist;

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    public String getoAuthCodeTodoist() {
        return oAuthCodeTodoist;
    }

    public void setoAuthCodeTodoist(String oAuthCodeTodoist) {
        this.oAuthCodeTodoist = oAuthCodeTodoist;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Medewerker [getVoornaam()=");
        builder.append(getVoornaam());
        builder.append(", getTussenvoegsel()=");
        builder.append(getTussenvoegsel());
        builder.append(", getAchternaam()=");
        builder.append(getAchternaam());
        builder.append(", getId()=");
        builder.append(getId());
        builder.append(", getIdentificatie()=");
        builder.append(getIdentificatie());
        builder.append(", getWachtwoord()=");
        builder.append(getWachtwoord());
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append(", hashCode()=");
        builder.append(hashCode());
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medewerker)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Medewerker that = (Medewerker) o;
        return Objects.equals(getKantoor(), that.getKantoor()) && Objects.equals(getoAuthCodeTodoist(), that.getoAuthCodeTodoist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKantoor(), getoAuthCodeTodoist());
    }

    @Override
    public String getName() {
        return this.getIdentificatie();
    }

}
