package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TELEFONIEBESTAND")
@NamedQueries({@NamedQuery(name = "TelefonieBestand.allesMetTelefoonnummer", query = "select tb from TelefonieBestand tb where tb.telefoonnummer = :telefoonnummer")})
public class TelefonieBestand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BESTANDSNAAM")
    private String bestandsnaam;
    @Column(name = "TELEFOONNUMMER")
    private String telefoonnummer;
    @Column(name = "TIJDSTIP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tijdstip;

    public TelefonieBestand() {
        //Deze is er omdat Hibernate het anders niet zo leuk vindt en deze comment is er omdat SonarQube het anders niet zo leuk vindt
    }

    public TelefonieBestand(String bestandsnaam, String telefoonnummer, LocalDateTime tijdstip) {
        this.bestandsnaam = bestandsnaam;
        this.telefoonnummer = telefoonnummer;
        this.setTijdstip(tijdstip);
    }

    public TelefonieBestand(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
        int datum = 3;
        int tijd = 4;

        String[] parts = bestandsnaam.split("-");
        if ("out".equals(parts[0])) {
            if (parts[1].length() == 10 || (parts[1].startsWith("0900") || parts[1].startsWith("0800"))) {
                telefoonnummer = parts[1];
            } else {
                telefoonnummer = parts[1] + parts[2];
                datum = 4;
                tijd = 5;
            }
            //voor de zekerheid
            if (telefoonnummer.length() > 10) {
                telefoonnummer = telefoonnummer.substring(0, 10);
            }
        } else {
            telefoonnummer = parts[2];
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("YYYYMMddHHmmss");
        try {
            setTijdstip(LocalDateTime.parse(parts[datum] + parts[tijd], dateTimeFormatter));
        } catch (Exception e) {
            setTijdstip(LocalDateTime.now());
            throw e;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsnaam() {
        return bestandsnaam;
    }

    public void setBestandsnaam(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public LocalDateTime getTijdstip() {
        return new LocalDateTime(tijdstip);
    }

    public void setTijdstip(LocalDateTime tijdstip) {
        this.tijdstip = tijdstip.toDate();
    }

    public boolean isUitgaand() {
        return this.bestandsnaam.startsWith("out");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelefonieBestand)) {
            return false;
        }
        TelefonieBestand that = (TelefonieBestand) o;
        return Objects.equals(getBestandsnaam(), that.getBestandsnaam()) &&
                Objects.equals(getTelefoonnummer(), that.getTelefoonnummer()) &&
                Objects.equals(getTijdstip(), that.getTijdstip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBestandsnaam(), getTelefoonnummer(), getTijdstip());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("bestandsnaam", bestandsnaam).append("telefoonnummer", telefoonnummer).append("tijdstip", tijdstip).append("uitgaand", isUitgaand()).toString();
    }
}
