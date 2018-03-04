package nl.lakedigital.djfc.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LICENTIE")
public abstract class Licentie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
}
