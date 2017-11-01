package nl.dias.domein;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import javax.persistence.*;

@Entity
@Table(name = "SOORTSCHADE")
@NamedQueries({ @NamedQuery(name = "SoortSchade.alles", query = "select s from SoortSchade s where s.ingebruik = '1'"),
        @NamedQuery(name = "SoortSchade.zoekOpOmschrijving", query = "select s from SoortSchade s where s.omschrijving like :omschrijving and s.ingebruik = '1'") })
public class SoortSchade implements PersistenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OMSCHRIJVING", length = 250, nullable = false)
    private String omschrijving;

    @Column(name = "INGEBRUIK")
    private boolean ingebruik;

    public SoortSchade() {
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

    @Override
    public String toString() {
        return omschrijving;
    }
}
