package nl.lakedigital.djfc.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorValue(value = "A")
public class AdministratieKantoor extends Licentie {
    public AdministratieKantoor() {
        super(365);
    }
}
