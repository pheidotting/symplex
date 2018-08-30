package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@Audited
@Entity
@Table(name = "POLIS")
@DiscriminatorColumn(name = "SOORT", length = 2)
@NamedQueries({@NamedQuery(name = "Polis.allesBijMaatschappij", query = "select p from Polis p where p.pakket.maatschappij = :maatschappij"),//
        @NamedQuery(name = "Polis.zoekOpPolisNummer", query = "select p from Polis p where p.polisNummer = :polisNummer"),//
        @NamedQuery(name = "Polis.allesVanRelatie", query = "select p from Polis p where p.pakket.entiteitId = :relatie and p.pakket.soortEntiteit = 'RELATIE'"),//
        @NamedQuery(name = "Polis.allesVanBedrijf", query = "select p from Polis p where p.pakket.entiteitId = :bedrijf and p.pakket.soortEntiteit = 'BEDRIJF'")//
})
public abstract class Polis implements Serializable, Cloneable {
    private static final long serialVersionUID = 1011438129295546984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS", length = 3)
    @Enumerated(EnumType.STRING)
    private StatusPolis status;

    @Column(name = "POLISNUMMER", length = 25)
    private String polisNummer;

    @Column(name = "KENMERK", length = 100)
    private String kenmerk;

    @Column(name = "INGANGSDATUM")
    @Temporal(TemporalType.DATE)
    private Date ingangsDatum;

    @Column(name = "EINDDATUM")
    @Temporal(TemporalType.DATE)
    private Date eindDatum;

    @AttributeOverride(name = "bedrag", column = @Column(name = "PREMIE"))
    private Bedrag premie;

    @Temporal(TemporalType.DATE)
    @Column(name = "WIJZIGINGSDATUM")
    private Date wijzigingsDatum;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROLONGATIEDATUM")
    private Date prolongatieDatum;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, name = "BETAALFREQUENTIE")
    private Betaalfrequentie betaalfrequentie;

    @Column(name = "DEKKING", length = 250)
    private String dekking;

    @Column(name = "VERZEKERDEZAAK", length = 250)
    private String verzekerdeZaak;

    @Column(name = "OMSCHRIJVING", columnDefinition = "varchar(2500)")
    private String omschrijvingVerzekering;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAKKET")
    private Pakket pakket;

    @Transient
    private String identificatie;
    @Transient
    private List<Schade> schades;

    public Polis() {
    }

    public Polis(Pakket pakket) {
        this.pakket = pakket;
        this.pakket.getPolissen().add(this);
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public List<Schade> getSchades() {
        if (schades == null) {
            schades = new ArrayList<>();
        }
        return schades;
    }

    public void setSchades(List<Schade> schades) {
        this.schades = schades;
    }

    public abstract SoortVerzekering getSoortVerzekering();

    public abstract String getSchermNaam();

    protected String getSchermNaamDefault(String canonicalName) {
        String pakket = this.getClass().getPackage().toString().replace("package ", "") + ".";
        return maakNaam(canonicalName.replace("Verzekering", "").replace(pakket, ""));
    }

    public abstract Polis nieuweInstantie(Pakket pakket);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public String getKenmerk() {
        return kenmerk;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public StatusPolis getStatus() {
        return status;
    }

    public void setStatus(StatusPolis status) {
        this.status = status;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
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
        }
        if (eindDatum != null) {
            this.eindDatum = eindDatum.toDate();
        }
    }

    public Bedrag getPremie() {
        return premie;
    }

    public void setPremie(Bedrag premie) {
        this.premie = premie;
    }


    public LocalDate getWijzigingsDatum() {
        if (wijzigingsDatum == null) {
            return null;
        }
        return new LocalDate(wijzigingsDatum);
    }

    public void setWijzigingsDatum(LocalDate wijzigingsDatum) {
        if (wijzigingsDatum == null) {
            this.wijzigingsDatum = null;
        } else {
            this.wijzigingsDatum = wijzigingsDatum.toDate();
        }
    }

    public LocalDate getProlongatieDatum() {
        if (prolongatieDatum == null) {
            return null;
        }
        return new LocalDate(prolongatieDatum);
    }

    public void setProlongatieDatum(LocalDate prolongatieDatum) {
        if (prolongatieDatum == null) {
            this.prolongatieDatum = null;
        } else {
            this.prolongatieDatum = prolongatieDatum.toDate();
        }
    }

    public Betaalfrequentie getBetaalfrequentie() {
        return betaalfrequentie;
    }

    public void setBetaalfrequentie(Betaalfrequentie betaalfrequentie) {
        this.betaalfrequentie = betaalfrequentie;
    }

    public String getDekking() {
        return dekking;
    }

    public void setDekking(String dekking) {
        this.dekking = dekking;
    }

    public String getVerzekerdeZaak() {
        return verzekerdeZaak;
    }

    public void setVerzekerdeZaak(String verzekerdeZaak) {
        this.verzekerdeZaak = verzekerdeZaak;
    }


    public String getOmschrijvingVerzekering() {
        return omschrijvingVerzekering;
    }

    public void setOmschrijvingVerzekering(String omschrijvingVerzekering) {
        this.omschrijvingVerzekering = omschrijvingVerzekering;
    }

    public Pakket getPakket() {
        return pakket;
    }

    public void setPakket(Pakket pakket) {
        this.pakket = pakket;
    }

    protected String maakNaam(String klasse) {
        StringBuilder sb = new StringBuilder();

        sb.append(klasse.substring(0, 1));

        for (int i = 1; i < klasse.length() - 1; i++) {
            if (klasse.substring(i, i + 1).equals(klasse.substring(i, i + 1).toUpperCase())) {
                sb.append(" ");
            }
            sb.append(klasse.substring(i, i + 1));
        }

        sb.append(klasse.substring(klasse.length() - 1));

        String metSpaties = sb.toString();

        String[] s = metSpaties.split(" ");

        sb = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (s[i].length() > 1) {
                sb.append(" ");
            }
            sb.append(s[i]);
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Polis)) {
            return false;
        }
        Polis polis = (Polis) o;
        return Objects.equals(getId(), polis.getId()) && getStatus() == polis.getStatus() && Objects.equals(getPolisNummer(), polis.getPolisNummer()) && Objects.equals(getKenmerk(), polis.getKenmerk()) && Objects.equals(getIngangsDatum(), polis.getIngangsDatum()) && Objects.equals(getEindDatum(), polis.getEindDatum()) && Objects.equals(getPremie(), polis.getPremie()) && Objects.equals(getWijzigingsDatum(), polis.getWijzigingsDatum()) && Objects.equals(getProlongatieDatum(), polis.getProlongatieDatum()) && getBetaalfrequentie() == polis.getBetaalfrequentie() && Objects.equals(getDekking(), polis.getDekking()) && Objects.equals(getVerzekerdeZaak(), polis.getVerzekerdeZaak()) && Objects.equals(getOmschrijvingVerzekering(), polis.getOmschrijvingVerzekering());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getPolisNummer(), getKenmerk(), getIngangsDatum(), getEindDatum(), getPremie(), getWijzigingsDatum(), getProlongatieDatum(), getBetaalfrequentie(), getDekking(), getVerzekerdeZaak(), getOmschrijvingVerzekering());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("status", status).append("polisNummer", polisNummer).append("kenmerk", kenmerk).append("ingangsDatum", ingangsDatum).append("eindDatum", eindDatum).append("premie", premie).append("wijzigingsDatum", wijzigingsDatum).append("prolongatieDatum", prolongatieDatum).append("betaalfrequentie", betaalfrequentie).append("dekking", dekking).append("verzekerdeZaak", verzekerdeZaak).append("omschrijvingVerzekering", omschrijvingVerzekering).toString();
    }
}
