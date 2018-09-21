package nl.dias.domein;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;

import javax.persistence.*;

@Entity
@Table(name = "BELASTINGZAKEN")
@NamedQueries({//
        @NamedQuery(name = "Belastingzaken.zoekOpSoortEntiteitEnId", query = "select b from Belastingzaken b where b.soortEntiteit = :soortEntiteit AND b.entiteitId = :entiteitId"),//
})
public class Belastingzaken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTENTITEIT")
    private SoortEntiteit soortEntiteit;
    @Column(name = "ENTITEITID")
    private Long entiteitId;
    @Column(name = "JAAR")
    private Integer jaar;
    @Column(name = "SOORT")
    @Enumerated(EnumType.STRING)
    private SoortBelastingzaak soort;

    public enum SoortBelastingzaak {
        CONTRACTEN, JAARREKENING, IB, BTW, LOONBELASTING, OVERIG
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

    public SoortBelastingzaak getSoort() {
        return soort;
    }

    public void setSoort(SoortBelastingzaak soort) {
        this.soort = soort;
    }
}
