package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RISICOANALYSE")
@NamedQueries({@NamedQuery(name = "RisicoAnalyse.alleRisicoAnalysesBijBedrijf", query = "select ra from RisicoAnalyse ra where ra.bedrijf = :bedrijf")})
public class RisicoAnalyse implements Serializable, PersistenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "BEDRIJF")
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Bedrijf.class)
    private Bedrijf bedrijf;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }
}
