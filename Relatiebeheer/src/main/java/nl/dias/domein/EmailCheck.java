package nl.dias.domein;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
        super();
    }

    public EmailCheck(Long gebruiker, String mailadres) {
        super();
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("gebruiker", gebruiker).append("mailadres", mailadres).toString();
    }
}
