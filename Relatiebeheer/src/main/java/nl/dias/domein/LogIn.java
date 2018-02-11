package nl.dias.domein;

import org.joda.time.LocalDateTime;

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
        this.expireDatum = LocalDateTime.now().plusHours(2).toDateTime().toDate();
        System.out.println(this.expireDatum);
    }

    public LogIn(String token, Long gebruikerId) {
        this.token = token;
        this.gebruikerId = gebruikerId;
        this.expireDatum = LocalDateTime.now().plusHours(2).toDateTime().toDate();
        System.out.println(this.expireDatum);
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
