package nl.lakedigital.djfc.commons.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorValue(value = "B")
public class Brons extends Licentie {
    public Brons() {
        super(365);
    }
}
