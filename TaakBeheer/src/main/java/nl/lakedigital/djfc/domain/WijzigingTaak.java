package nl.lakedigital.djfc.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WIJZIGINGTAAK")
public class WijzigingTaak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TAAKID")
    private Long taak;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TaakStatus taakStatus;
    @Column(name = "TOEGEWEZENAAN")
    private Long toegewezenAan;
    @Column(name = "TIJDSTIP")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private Date tijdstip;
}
