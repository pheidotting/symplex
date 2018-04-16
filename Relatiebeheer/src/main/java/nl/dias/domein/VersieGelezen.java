package nl.dias.domein;

import javax.persistence.*;

@Entity
@Table(name = "VERSIEGELEZEN")
public class VersieGelezen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VERSIEID")
    private Long versie;

    @Column(name = "GEBRUIKERID")
    private Long gebruiker;

    public VersieGelezen() {
        super();
    }

    public VersieGelezen(Long versie, Long gebruiker) {
        this.versie = versie;
        this.gebruiker = gebruiker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersie() {
        return versie;
    }

    public void setVersie(Long versie) {
        this.versie = versie;
    }

    public Long getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Long gebruiker) {
        this.gebruiker = gebruiker;
    }
}