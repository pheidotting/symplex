package nl.lakedigital.djfc.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Audited
@Entity
@Table(name = "PAKKET")
public class Pakket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "POLISNUMMER", length = 25)
    private String polisNummer;

    @Column(name = "SOORTENTITEIT")
    @Enumerated(EnumType.STRING)
    private SoortEntiteit soortEntiteit;

    @Column(name = "ENTITEITID")
    private Long entiteitId;

    @Column(name = "MAATSCHAPPIJ")
    private Long maatschappij;

    @OneToMany(mappedBy = "pakket", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Polis> polissen;

    public Pakket() {
    }

    public Pakket(SoortEntiteit soortEntiteit, Long entiteitId) {
        this.soortEntiteit = soortEntiteit;
        this.entiteitId = entiteitId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public Long getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(Long maatschappij) {
        this.maatschappij = maatschappij;
    }

    public Set<Polis> getPolissen() {
        if (polissen == null) {
            polissen = new HashSet<>();
        }
        return polissen;
    }

    public void setPolissen(Set<Polis> polissen) {
        this.polissen = polissen;
    }
}
