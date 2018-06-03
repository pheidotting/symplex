package nl.lakedigital.djfc.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "WIJZIGINGTAAK")
@NamedQueries({//
        @NamedQuery(name = "WijzigingTaak.zoekBijTaak", query = "select w from WijzigingTaak w where w.taak = :taak")//
})
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
    private LocalDateTime tijdstip;

    public WijzigingTaak() {
    }

    public WijzigingTaak(Taak taak, TaakStatus taakStatus, Long toegewezenAan) {
        this.taak = taak.getId();
        this.taakStatus = taakStatus;
        this.toegewezenAan = toegewezenAan;
        this.tijdstip = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaak() {
        return taak;
    }

    public void setTaak(Long taak) {
        this.taak = taak;
    }

    public TaakStatus getTaakStatus() {
        return taakStatus;
    }

    public void setTaakStatus(TaakStatus taakStatus) {
        this.taakStatus = taakStatus;
    }

    public Long getToegewezenAan() {
        return toegewezenAan;
    }

    public void setToegewezenAan(Long toegewezenAan) {
        this.toegewezenAan = toegewezenAan;
    }

    public LocalDateTime getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(LocalDateTime tijdstip) {
        this.tijdstip = tijdstip;
    }
}
