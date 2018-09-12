package nl.lakedigital.djfc.domain.inkomend;

import nl.lakedigital.djfc.domain.SoortOpdracht;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "INKOMEND")
@DiscriminatorColumn(name = "SOORT", length = 3)
public class InkomendeOpdracht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "SOORTOPDRACHT")
    @Enumerated(EnumType.STRING)
    private SoortOpdracht soortOpdracht;
    @Column(name = "TRACKANDTRACEID")
    private String trackAndTraceId;
    @Column(name = "BERICHT")
    private String bericht;
    @Column(name = "TIJDSTIP")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstip;
    @OneToMany
    private Set<UitgaandeOpdracht> uitgaandeOpdrachten;

    public InkomendeOpdracht() {
        super();
    }

    public InkomendeOpdracht(SoortOpdracht soortOpdracht, String trackAndTraceId, String bericht) {
        this.soortOpdracht = soortOpdracht;
        this.trackAndTraceId = trackAndTraceId;
        this.bericht = bericht;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackAndTraceId() {
        return trackAndTraceId;
    }

    public void setTrackAndTraceId(String trackAndTraceId) {
        this.trackAndTraceId = trackAndTraceId;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public LocalDateTime getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(LocalDateTime tijdstip) {
        this.tijdstip = tijdstip;
    }

    public Set<UitgaandeOpdracht> getUitgaandeOpdrachten() {
        return uitgaandeOpdrachten;
    }

    public void setUitgaandeOpdrachten(Set<UitgaandeOpdracht> uitgaandeOpdrachten) {
        this.uitgaandeOpdrachten = uitgaandeOpdrachten;
    }
}
