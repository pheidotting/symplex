package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "B")
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM")) })
public class Beheerder extends Gebruiker implements PersistenceObject, Serializable {
    private static final long serialVersionUID = 3403882985064145165L;

    @Override
    public String getName() {
        return this.getIdentificatie();
    }

}
