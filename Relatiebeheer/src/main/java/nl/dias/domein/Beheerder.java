package nl.dias.domein;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "B")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
public class Beheerder extends Gebruiker implements Serializable {
    private static final long serialVersionUID = 3403882985064145165L;

    public String getName() {
        return this.getIdentificatie();
    }

}
