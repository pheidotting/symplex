package nl.dias.domein;

import javax.persistence.*;

@Entity
@Table(name = "MAILCHECK")
public class EmailCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "GEBRUIKER")
    private Long gebruiker;

    @Column(name = "MAILADRES")
    private String mailadres;

    public EmailCheck() {
    }

    public EmailCheck(Long gebruiker, String mailadres) {
        this.gebruiker = gebruiker;
        this.mailadres = mailadres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Long gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String getMailadres() {
        return mailadres;
    }

    public void setMailadres(String mailadres) {
        this.mailadres = mailadres;
    }
}
