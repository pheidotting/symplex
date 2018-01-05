package nl.dias.domein;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INLOGPOGING")
@NamedQueries({@NamedQuery(name = "InlogPoging.verwijderOudeMislukte", query = "DELETE FROM InlogPoging i WHERE i.gebruikerId = :gebruikerId AND i.gelukt = false"), @NamedQuery(name = "InlopPoging.zoekFouteInlogPogingen", query = "SELECT i FROM InlogPoging i WHERE i.gebruikerId = :gebruikerId AND i.gelukt = false AND i.tijdstip > :tijdstip")})
public class InlogPoging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "GEBRUIKERID")
    private Long gebruikerId;

    @CollectionTable(name = "TIJDSTIP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tijdstip;

    @Column(name = "GELUKT")
    private Boolean gelukt;

    public InlogPoging() {
    }

    public InlogPoging(Long gebruikerId, Boolean gelukt) {
        this.gebruikerId = gebruikerId;
        this.tijdstip = new Date();
        this.gelukt = gelukt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGebruikerId() {
        return gebruikerId;
    }

    public void setGebruikerId(Long gebruikerId) {
        this.gebruikerId = gebruikerId;
    }

    public Date getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(Date tijdstip) {
        this.tijdstip = tijdstip;
    }

    public Boolean getGelukt() {
        return gelukt;
    }

    public void setGelukt(Boolean gelukt) {
        this.gelukt = gelukt;
    }
}
