package nl.dias.domein;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOGIN")
public class LogIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "GEBRUIKERID")
    private Long gebruikerId;

    @Column(name = "EXPIREDATUM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDatum;

    public LogIn() {
        expireDatum = new Date();
    }

    public LogIn(String token, Long gebruikerId) {
        this.token = token;
        this.gebruikerId = gebruikerId;
        expireDatum = new Date();
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGebruikerId() {
        return gebruikerId;
    }

    public void setGebruikerId(Long gebruikerId) {
        this.gebruikerId = gebruikerId;
    }

    public Date getExpireDatum() {
        return expireDatum;
    }
}
