package nl.lakedigital.djfc.commons.domain;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TAAK")
@Audited
@NamedQueries({//
        @NamedQuery(name = "Taak.zoekOpSoortEntiteitEnEntiteitId", query = "select t from Taak t where t.entiteitId = :entiteitId and t.soortEntiteit = :soortEntiteit"),//
        @NamedQuery(name = "Taak.allesOpenstaand", query = "select t from Taak t where t.tijdstipAfgehandeld is null")//
})
public class Taak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TIJDSTIPCREATIE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipCreatie;
    @Column(name = "DEADLINE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate deadline;
    @Column(name = "TIJDSTIPAFGEHANDELD")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipAfgehandeld;
    @Column(name = "TITEL")
    private String titel;
    @Column(name = "OMSCHRIJVING", length = 2500)
    private String omschrijving;
    @Column(name = "ENTITEITID")
    private Long entiteitId;
    @Column(name = "SOORTENTITEIT")
    @Enumerated(EnumType.STRING)
    private SoortEntiteit soortEntiteit;
    @Transient
    private List<WijzigingTaak> wijzigingTaak;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTijdstipCreatie() {
        return tijdstipCreatie;
    }

    public void setTijdstipCreatie(LocalDateTime tijdstipCreatie) {
        this.tijdstipCreatie = tijdstipCreatie;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getTijdstipAfgehandeld() {
        return tijdstipAfgehandeld;
    }

    public void setTijdstipAfgehandeld(LocalDateTime tijdstipAfgehandeld) {
        this.tijdstipAfgehandeld = tijdstipAfgehandeld;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public List<WijzigingTaak> getWijzigingTaak() {
        if (wijzigingTaak == null) {
            wijzigingTaak = new ArrayList<>();
        }
        return wijzigingTaak;
    }

    public void setWijzigingTaak(List<WijzigingTaak> wijzigingTaak) {
        this.wijzigingTaak = wijzigingTaak;
    }
}
