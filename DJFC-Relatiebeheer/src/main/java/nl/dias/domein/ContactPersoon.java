package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Audited
@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "C")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQuery(name = "ContactPersoon.alleContactPersonen", query = "select cp from ContactPersoon cp where cp.bedrijf = :bedrijf")
public class ContactPersoon extends Gebruiker implements Serializable, PersistenceObject {
    private static final long serialVersionUID = -4313251874716582151L;

    @Column(name = "BEDRIJF")
    private Long bedrijf;

    @Column(name = "FUNCTIE")
    private String functie;

    public Long getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Long bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    @Override
    public String getName() {
        return this.getIdentificatie();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("bedrijf", bedrijf).append("functie", functie).append("name", getName()).toString();
    }
}
