package nl.lakedigital.djfc.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "TAAK")
//@Audited
public class Taak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TIJDSTIPCREATIE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipCreatie;
    @Column(name = "TIJDSTIP")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstip;
    @Column(name = "TIJDSTIPAFGEHANDELD")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipAfgehandeld;
    @Column(name = "TITEL")
    private String titel;
    @Column(name = "OMSCHRIJVING")
    private String omschrijving;
    @Column(name = "ENTITEITID")
    private Long entiteitId;
    @Column(name = "SOORTENTITEIT")
    @Enumerated(EnumType.STRING)
    private SoortEntiteit soortEntiteit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
