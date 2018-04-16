package nl.lakedigital.djfc.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorValue(value = "G")
public class Goud extends Licentie {
    public Goud() {
        super(365);
    }
}
