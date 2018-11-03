package nl.lakedigital.djfc.commons.domain.inkomend;

import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INKOMEND")
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
    @Column(name = "BERICHT", columnDefinition = "text")
    private String bericht;
    @Column(name = "TIJDSTIP")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstip;
    @OneToMany(mappedBy = "inkomendeOpdracht", cascade = CascadeType.ALL)
    private Set<UitgaandeOpdracht> uitgaandeOpdrachten;

    public InkomendeOpdracht() {
        super();
    }

    public InkomendeOpdracht(SoortOpdracht soortOpdracht, String trackAndTraceId, String bericht) {
        this.soortOpdracht = soortOpdracht;
        this.trackAndTraceId = trackAndTraceId;
        this.bericht = bericht;
        this.tijdstip = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SoortOpdracht getSoortOpdracht() {
        return soortOpdracht;
    }

    public void setSoortOpdracht(SoortOpdracht soortOpdracht) {
        this.soortOpdracht = soortOpdracht;
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
        if (uitgaandeOpdrachten == null) {
            uitgaandeOpdrachten = new HashSet<>();
        }
        return uitgaandeOpdrachten;
    }

    public void setUitgaandeOpdrachten(Set<UitgaandeOpdracht> uitgaandeOpdrachten) {
        this.uitgaandeOpdrachten = uitgaandeOpdrachten;
    }
}
