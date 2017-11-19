package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SOORTHYPOTHEEK")
@NamedQueries({ @NamedQuery(name = "SoortHypotheek.allesInGebruik", query = "select s from SoortHypotheek s where s.ingebruik = '1'") })
public class SoortHypotheek implements PersistenceObject, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OMSCHRIJVING", length = 250, nullable = false)
    private String omschrijving;

    @Column(name = "INGEBRUIK")
    private boolean ingebruik;

    public SoortHypotheek() {
        ingebruik = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public boolean isIngebruik() {
        return ingebruik;
    }

    public void setIngebruik(boolean ingebruik) {
        this.ingebruik = ingebruik;
    }
}
