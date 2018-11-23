package nl.lakedigital.djfc.commons.domain.uitgaand;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.inkomend.InkomendeOpdracht;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "UITGAAND")
@NamedQueries({//
        @NamedQuery(name = "UitgaandeOpdracht.teVersturenUitgaandeOpdrachten", query = "select u from UitgaandeOpdracht u where tijdstipVerzonden is null and wachtenOp is null"),//
        @NamedQuery(name = "UitgaandeOpdracht.teVersturenUitgaandeOpdrachtenOpTAndTEnWachtenOp", query = "select u from UitgaandeOpdracht u where tijdstipVerzonden is null and wachtenOp = :wachtenOp AND inkomendeOpdracht.trackAndTraceId = :trackAndTraceId"),//
        @NamedQuery(name = "UitgaandeOpdracht.zoekOpBerichtId", query = "select u from UitgaandeOpdracht u where inkomendeOpdracht.trackAndTraceId = :trackAndTraceId and tijdstipAfgerond is null and berichtId = :berichtId"),//
        @NamedQuery(name = "UitgaandeOpdracht.zoekObvTrackAndTrackeId", query = "select u from UitgaandeOpdracht u where inkomendeOpdracht.trackAndTraceId = :trackAndTraceId")//
})
public class UitgaandeOpdracht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "SOORTOPDRACHT")
    @Enumerated(EnumType.STRING)
    private SoortEntiteit soortEntiteit;
    @Column(name = "TIJDSTIPVERZONDEN")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipVerzonden;
    @Column(name = "TIJDSTIPAFGEROND")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstipAfgerond;
    @Column(name = "BERICHT", columnDefinition = "text")
    private String bericht;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "INKOMENDEOPDRACHT")
    private InkomendeOpdracht inkomendeOpdracht;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UitgaandeOpdracht.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "WACHTENOP")
    private UitgaandeOpdracht wachtenOp;
    @Column(name = "BERICHTID")
    private String berichtId;

    public UitgaandeOpdracht() {
        this.berichtId = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
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

    public String getBerichtId() {
        return berichtId;
    }

    public void setBerichtId(String berichtId) {
        this.berichtId = berichtId;
    }
}
