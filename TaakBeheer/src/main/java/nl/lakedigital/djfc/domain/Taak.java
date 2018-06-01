package nl.lakedigital.djfc.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "TAAK")
@Audited
public class Taak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
}
