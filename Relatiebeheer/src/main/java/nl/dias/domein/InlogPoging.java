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

    @Column(name = "IPADRES")
    private String ipAdres;

    @Column(name = "ADRES")
    private String adres;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    public InlogPoging() {
    }

    public InlogPoging(Long gebruikerId, Boolean gelukt, String ipAdres, String adres, Double latitude, Double longitude) {
        this.gebruikerId = gebruikerId;
        this.tijdstip = new Date();
        this.gelukt = gelukt;
        this.ipAdres = ipAdres;
        this.adres = adres;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getIpAdres() {
        return ipAdres;
    }

    public void setIpAdres(String ipAdres) {
        this.ipAdres = ipAdres;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
