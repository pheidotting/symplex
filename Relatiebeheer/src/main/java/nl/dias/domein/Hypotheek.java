package nl.dias.domein;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Audited
@Entity
@Table(name = "HYPOTHEEK")
@NamedQueries({@NamedQuery(name = "Hypotheek.allesVanRelatie", query = "select h from Hypotheek h where h.relatie = :relatie and size(h.hypotheekPakket.hypotheken) = 1"),//
        @NamedQuery(name = "Hypotheek.allesVanRelatieInclDePakketten", query = "select h from Hypotheek h where h.relatie = :relatie"),//
        @NamedQuery(name = "Hypotheek.allesVanRelatieInEenPakket", query = "select h from Hypotheek h where h.relatie = :relatie and size(h.hypotheekPakket.hypotheken) >= 2")//
})
public class Hypotheek implements Serializable {
    private static final long serialVersionUID = -8709743283669873667L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "RELATIE")
    //    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true, targetEntity = Relatie.class)
    protected Long relatie;

    @JoinColumn(name = "SOORT")
    @ManyToOne
    @NotAudited
    private SoortHypotheek hypotheekVorm;
    @Column(name = "OMSCHRIJVING", length = 1000, nullable = true)
    private String omschrijving;
    @AttributeOverride(name = "bedrag", column = @Column(name = "HYPOTHEEKBEDRAG"))
    private Bedrag hypotheekBedrag;
    @Column(name = "RENTE")
    private BigDecimal rente;
    @AttributeOverride(name = "bedrag", column = @Column(name = "MARKTWAARDE"))
    private Bedrag marktWaarde;
    @Column(name = "ONDERPAND")
    private String onderpand;
    @AttributeOverride(name = "bedrag", column = @Column(name = "KOOPSOM"))
    private Bedrag koopsom;
    @AttributeOverride(name = "bedrag", column = @Column(name = "VRIJEVERKOOPWAARDE"))
    private Bedrag vrijeVerkoopWaarde;
    @Temporal(TemporalType.DATE)
    @Column(name = "TAXATIEDATUM")
    private Date taxatieDatum;
    @AttributeOverride(name = "bedrag", column = @Column(name = "WOZWAARDE"))
    private Bedrag wozWaarde;
    @AttributeOverride(name = "bedrag", column = @Column(name = "WAARDEVOORVERBOUWING"))
    private Bedrag waardeVoorVerbouwing;
    @AttributeOverride(name = "bedrag", column = @Column(name = "WAARDENAVERBOUWING"))
    private Bedrag waardeNaVerbouwing;
    @Temporal(TemporalType.DATE)
    @Column(name = "INGANGSDATUM")
    private Date ingangsDatum;
    @Temporal(TemporalType.DATE)
    @Column(name = "EINDDATUM")
    private Date eindDatum;
    @Column(name = "DUUR")
    private Long duur;
    @Temporal(TemporalType.DATE)
    @Column(name = "INGANGSDATUMRENTEVASTEPERIODE")
    private Date ingangsDatumRenteVastePeriode;
    @Temporal(TemporalType.DATE)
    @Column(name = "EINDDATUMRENTEVASTEPERIODE")
    private Date eindDatumRenteVastePeriode;
    @Column(name = "DUURRENTEVASTEPERIODE")
    private Long duurRenteVastePeriode;
    @Column(name = "LENINGNUMMER", length = 50)
    private String leningNummer;
    @Column(name = "BANK", nullable = true, length = 25)
    private String bank;
    @ManyToOne
    @JoinColumn(name = "PAKKET")
    private HypotheekPakket hypotheekPakket;
    @AttributeOverride(name = "bedrag", column = @Column(name = "BOXI"))
    private Bedrag boxI;
    @AttributeOverride(name = "bedrag", column = @Column(name = "BOXIII"))
    private Bedrag boxIII;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SoortHypotheek getHypotheekVorm() {
        return hypotheekVorm;
    }

    public void setHypotheekVorm(SoortHypotheek hypotheekVorm) {
        this.hypotheekVorm = hypotheekVorm;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Bedrag getHypotheekBedrag() {
        return hypotheekBedrag;
    }

    public void setHypotheekBedrag(Bedrag hypotheekBedrag) {
        this.hypotheekBedrag = hypotheekBedrag;
    }

    public BigDecimal getRente() {
        return rente;
    }

    public void setRente(BigDecimal rente) {
        this.rente = rente;
    }

    public Bedrag getMarktWaarde() {
        return marktWaarde;
    }

    public void setMarktWaarde(Bedrag marktWaarde) {
        this.marktWaarde = marktWaarde;
    }

    public String getOnderpand() {
        return onderpand;
    }

    public void setOnderpand(String onderpand) {
        this.onderpand = onderpand;
    }

    public Bedrag getKoopsom() {
        return koopsom;
    }

    public void setKoopsom(Bedrag koopsom) {
        this.koopsom = koopsom;
    }

    public Bedrag getVrijeVerkoopWaarde() {
        return vrijeVerkoopWaarde;
    }

    public void setVrijeVerkoopWaarde(Bedrag vrijeVerkoopWaarde) {
        this.vrijeVerkoopWaarde = vrijeVerkoopWaarde;
    }

    public LocalDate getTaxatieDatum() {
        if (taxatieDatum == null) {
            return null;
        }
        return new LocalDate(taxatieDatum);
    }

    public void setTaxatieDatum(LocalDate taxatieDatum) {
        if (taxatieDatum == null) {
            this.taxatieDatum = null;
        } else {
            this.taxatieDatum = taxatieDatum.toDate();
        }
    }

    public Bedrag getWozWaarde() {
        return wozWaarde;
    }

    public void setWozWaarde(Bedrag wozWaarde) {
        this.wozWaarde = wozWaarde;
    }

    public Bedrag getWaardeVoorVerbouwing() {
        return waardeVoorVerbouwing;
    }

    public void setWaardeVoorVerbouwing(Bedrag waardeVoorVerbouwing) {
        this.waardeVoorVerbouwing = waardeVoorVerbouwing;
    }

    public Bedrag getWaardeNaVerbouwing() {
        return waardeNaVerbouwing;
    }

    public void setWaardeNaVerbouwing(Bedrag waardeNaVerbouwing) {
        this.waardeNaVerbouwing = waardeNaVerbouwing;
    }

    public LocalDate getIngangsDatum() {
        if (ingangsDatum == null) {
            return null;
        }
        return new LocalDate(ingangsDatum);
    }

    public void setIngangsDatum(LocalDate ingangsDatum) {
        if (ingangsDatum == null) {
            this.ingangsDatum = null;
        } else {
            this.ingangsDatum = ingangsDatum.toDate();
        }
    }

    public LocalDate getEindDatum() {
        if (eindDatum == null) {
            return null;
        }
        return new LocalDate(eindDatum);
    }

    public void setEindDatum(LocalDate eindDatum) {
        if (eindDatum == null) {
            this.eindDatum = null;
        } else {
            this.eindDatum = eindDatum.toDate();
        }
    }

    public Long getDuur() {
        return duur;
    }

    public void setDuur(Long duur) {
        this.duur = duur;
    }

    public LocalDate getIngangsDatumRenteVastePeriode() {
        if (ingangsDatumRenteVastePeriode == null) {
            return null;
        }
        return new LocalDate(ingangsDatumRenteVastePeriode);
    }

    public void setIngangsDatumRenteVastePeriode(LocalDate ingangsDatumRenteVastePeriode) {
        if (ingangsDatumRenteVastePeriode == null) {
            this.ingangsDatumRenteVastePeriode = null;
        } else {
            this.ingangsDatumRenteVastePeriode = ingangsDatumRenteVastePeriode.toDate();
        }
    }

    public LocalDate getEindDatumRenteVastePeriode() {
        if (eindDatumRenteVastePeriode == null) {
            return null;
        }
        return new LocalDate(eindDatumRenteVastePeriode);
    }

    public void setEindDatumRenteVastePeriode(LocalDate eindDatumRenteVastePeriode) {
        if (eindDatumRenteVastePeriode == null) {
            this.eindDatumRenteVastePeriode = null;
        } else {
            this.eindDatumRenteVastePeriode = eindDatumRenteVastePeriode.toDate();
        }
    }

    public Long getDuurRenteVastePeriode() {
        return duurRenteVastePeriode;
    }

    public void setDuurRenteVastePeriode(Long duurRenteVastePeriode) {
        this.duurRenteVastePeriode = duurRenteVastePeriode;
    }

    public Long getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie.getId();
    }

    public String getLeningNummer() {
        return leningNummer;
    }

    public void setLeningNummer(String leningNummer) {
        this.leningNummer = leningNummer;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public HypotheekPakket getHypotheekPakket() {
        return hypotheekPakket;
    }

    public void setHypotheekPakket(HypotheekPakket hypotheekPakket) {
        this.hypotheekPakket = hypotheekPakket;
    }

    public Bedrag getBoxI() {
        return boxI;
    }

    public void setBoxI(Bedrag boxI) {
        this.boxI = boxI;
    }

    public Bedrag getBoxIII() {
        return boxIII;
    }

    public void setBoxIII(Bedrag boxIII) {
        this.boxIII = boxIII;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Hypotheek)) {
            return false;
        }
        Hypotheek rhs = (Hypotheek) object;
        return new EqualsBuilder().append(this.taxatieDatum, rhs.taxatieDatum).append(this.eindDatum, rhs.eindDatum).append(this.koopsom, rhs.koopsom).append(this.relatie, rhs.relatie).append(this.hypotheekBedrag, rhs.hypotheekBedrag).append(this.rente, rhs.rente).append(this.hypotheekVorm, rhs.hypotheekVorm).append(this.duur, rhs.duur).append(this.waardeVoorVerbouwing, rhs.waardeVoorVerbouwing).append(this.id, rhs.id).append(this.onderpand, rhs.onderpand).append(this.duurRenteVastePeriode, rhs.duurRenteVastePeriode).append(this.vrijeVerkoopWaarde, rhs.vrijeVerkoopWaarde).append(this.ingangsDatum, rhs.ingangsDatum).append(this.ingangsDatumRenteVastePeriode, rhs.ingangsDatumRenteVastePeriode).append(this.waardeNaVerbouwing, rhs.waardeNaVerbouwing).append(this.marktWaarde, rhs.marktWaarde).append(this.wozWaarde, rhs.wozWaarde).append(this.omschrijving, rhs.omschrijving).append(this.eindDatumRenteVastePeriode, rhs.eindDatumRenteVastePeriode).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.taxatieDatum).append(this.eindDatum).append(this.koopsom).append(this.relatie).append(this.hypotheekBedrag).append(this.rente).append(this.hypotheekVorm).append(this.duur).append(this.waardeVoorVerbouwing).append(this.id).append(this.onderpand).append(this.duurRenteVastePeriode).append(this.vrijeVerkoopWaarde).append(this.ingangsDatum).append(this.ingangsDatumRenteVastePeriode).append(this.waardeNaVerbouwing).append(this.marktWaarde).append(this.wozWaarde).append(this.omschrijving).append(this.eindDatumRenteVastePeriode).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taxatieDatum", this.taxatieDatum + "\n").append("eindDatum", this.eindDatum + "\n").append("koopsom", this.koopsom + "\n").append("hypotheekBedrag", this.hypotheekBedrag + "\n").append("rente", this.rente + "\n").append("hypotheekVorm", this.hypotheekVorm + "\n").append("duur", this.duur + "\n").append("waardeVoorVerbouwing", this.waardeVoorVerbouwing + "\n").append("id", this.id + "\n").append("onderpand", this.onderpand + "\n").append("duurRenteVastePeriode", this.duurRenteVastePeriode + "\n").append("vrijeVerkoopWaarde", this.vrijeVerkoopWaarde + "\n").append("ingangsDatum", this.ingangsDatum + "\n").append("ingangsDatumRenteVastePeriode", this.ingangsDatumRenteVastePeriode + "\n").append("waardeNaVerbouwing", this.waardeNaVerbouwing + "\n").append("marktWaarde", this.marktWaarde + "\n").append("wozWaarde", this.wozWaarde + "\n").append("omschrijving", this.omschrijving + "\n").append("eindDatumRenteVastePeriode", this.eindDatumRenteVastePeriode + "\n").append("leningNummer", this.leningNummer + "\n").append("hypotheekPakket", this.hypotheekPakket + "\n").toString();
    }

}
