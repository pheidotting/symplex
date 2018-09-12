package nl.lakedigital.djfc.domain.uitgaand;

import nl.lakedigital.djfc.domain.SoortOpdracht;
import nl.lakedigital.djfc.domain.inkomend.InkomendeOpdracht;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "UITGAAND")
@DiscriminatorColumn(name = "SOORT", length = 3)
public class UitgaandeOpdracht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "SOORTOPDRACHT")
    @Enumerated(EnumType.STRING)
    private SoortOpdracht soortOpdracht;
    @Column(name = "TIJDSTIPVERZONDEN")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipVerzonden;
    @Column(name = "TIJDSTIPAFGEROND")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipAfgerond;
    @Column(name = "BERICHT")
    private String bericht;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "INKOMENDEOPDRACHT")
    private InkomendeOpdracht inkomendeOpdracht;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WACHTENOP")
    private UitgaandeOpdracht wachtenOp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTijdstipVerzonden() {
        return tijdstipVerzonden;
    }

    public void setTijdstipVerzonden(LocalDateTime tijdstipVerzonden) {
        this.tijdstipVerzonden = tijdstipVerzonden;
    }

    public LocalDateTime getTijdstipAfgerond() {
        return tijdstipAfgerond;
    }

    public void setTijdstipAfgerond(LocalDateTime tijdstipAfgerond) {
        this.tijdstipAfgerond = tijdstipAfgerond;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public InkomendeOpdracht getInkomendeOpdracht() {
        return inkomendeOpdracht;
    }

    public void setInkomendeOpdracht(InkomendeOpdracht inkomendeOpdracht) {
        this.inkomendeOpdracht = inkomendeOpdracht;
    }

    public UitgaandeOpdracht getWachtenOp() {
        return wachtenOp;
    }

    public void setWachtenOp(UitgaandeOpdracht wachtenOp) {
        this.wachtenOp = wachtenOp;
    }
}
