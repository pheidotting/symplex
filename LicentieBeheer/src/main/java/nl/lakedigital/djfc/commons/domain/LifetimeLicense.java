package nl.lakedigital.djfc.commons.domain;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LICENTIE")
@DiscriminatorValue(value = "L")
public class LifetimeLicense extends Licentie {
}
