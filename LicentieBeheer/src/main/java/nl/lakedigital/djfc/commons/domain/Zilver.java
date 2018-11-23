package nl.lakedigital.djfc.commons.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorValue(value = "Z")
public class Zilver extends Licentie {
    public Zilver() {
        super(365);
    }
}
