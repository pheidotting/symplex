package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Audited
@Entity
@Table(name = "SCHADE")
@NamedQueries({@NamedQuery(name = "Schade.zoekOpschadeNummerMaatschappij", query = "select s from Schade s where s.schadeNummerMaatschappij = :schadeNummerMaatschappij"),//
        @NamedQuery(name = "Schade.allesBijPolis", query = "select s from Schade s where s.polis = :polis")})
public class Schade  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "POLIS", nullable = true)
    private Long polis;

    @Column(length = 25, name = "SCHADENUMMERMAATSCHAPPIJ", nullable = false)
    private String schadeNummerMaatschappij;

    @Column(length = 25, name = "SCHADENUMMERTUSSENPERSOON")
    private String schadeNummerTussenPersoon;

    @JoinColumn(name = "SOORT", nullable = true)
    @ManyToOne
    private SoortSchade soortSchade;

    @Column(name = "SOORTSCHADEONGEDEFINIEERD", length = 100)
    private String soortSchadeOngedefinieerd;

    @Column(length = 50, name = "LOCATIE")
    private String locatie;

    @JoinColumn(name = "STATUS", nullable = false)
    @ManyToOne
    private StatusSchade statusSchade;

    @Column(name = "DATUMTIJD", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdSchade;

    @Column(name = "DATUMTIJDMELDING", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdMelding;

    @Column(name = "DATUMAFGEHANDELD")
    @Temporal(TemporalType.DATE)
    private Date datumAfgehandeld;

    @AttributeOverride(name = "bedrag", column = @Column(name = "EIGENRISICO"))
    private Bedrag eigenRisico;

    @Column(length = 1000, name = "OMSCHRIJVING")
    private String omschrijving;

    @Transient
    private String identificatie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolis() {
        return polis;
    }

    public void setPolis(Long polis) {
        this.polis = polis;
    }

    public String getSchadeNummerMaatschappij() {
        return schadeNummerMaatschappij;
    }

    public void setSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        this.schadeNummerMaatschappij = schadeNummerMaatschappij;
    }

    public String getSchadeNummerTussenPersoon() {
        return schadeNummerTussenPersoon;
    }

    public void setSchadeNummerTussenPersoon(String schadeNummerTussenPersoon) {
        this.schadeNummerTussenPersoon = schadeNummerTussenPersoon;
    }

    public SoortSchade getSoortSchade() {
        return soortSchade;
    }

    public void setSoortSchade(SoortSchade soortSchade) {
        this.soortSchade = soortSchade;
    }

    public String getSoortSchadeOngedefinieerd() {
        return soortSchadeOngedefinieerd;
    }

    public void setSoortSchadeOngedefinieerd(String soortSchadeOngedefinieerd) {
        this.soortSchadeOngedefinieerd = soortSchadeOngedefinieerd;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public StatusSchade getStatusSchade() {
        return statusSchade;
    }

    public void setStatusSchade(StatusSchade statusSchade) {
        this.statusSchade = statusSchade;
    }

    public LocalDateTime getDatumTijdSchade() {
        if (datumTijdSchade != null) {
            return new LocalDateTime(datumTijdSchade);
        }
        return null;
    }

    public void setDatumTijdSchade(LocalDateTime datumTijdSchade) {
        this.datumTijdSchade = datumTijdSchade.toDate();
    }

    public LocalDateTime getDatumTijdMelding() {
        return new LocalDateTime(datumTijdMelding);
    }

    public void setDatumTijdMelding(LocalDateTime datumTijdMelding) {
        this.datumTijdMelding = datumTijdMelding.toDate();
    }

    public LocalDate getDatumAfgehandeld() {
        if (datumAfgehandeld != null) {
            return new LocalDate(datumAfgehandeld);
        }
        return null;
    }

    public void setDatumAfgehandeld(LocalDate datumAfgehandeld) {
        this.datumAfgehandeld = datumAfgehandeld == null ? null : datumAfgehandeld.toDate();
    }

    public Bedrag getEigenRisico() {
        return eigenRisico;
    }

    public void setEigenRisico(Bedrag eigenRisico) {
        this.eigenRisico = eigenRisico;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schade)) {
            return false;
        }
        Schade schade = (Schade) o;
        return Objects.equals(getId(), schade.getId()) &&
                Objects.equals(getPolis(), schade.getPolis()) &&
                Objects.equals(getSchadeNummerMaatschappij(), schade.getSchadeNummerMaatschappij()) &&
                Objects.equals(getSchadeNummerTussenPersoon(), schade.getSchadeNummerTussenPersoon()) &&
                Objects.equals(getSoortSchade(), schade.getSoortSchade()) &&
                Objects.equals(getSoortSchadeOngedefinieerd(), schade.getSoortSchadeOngedefinieerd()) &&
                Objects.equals(getLocatie(), schade.getLocatie()) &&
                Objects.equals(getStatusSchade(), schade.getStatusSchade()) &&
                Objects.equals(getDatumTijdSchade(), schade.getDatumTijdSchade()) &&
                Objects.equals(getDatumTijdMelding(), schade.getDatumTijdMelding()) &&
                Objects.equals(getDatumAfgehandeld(), schade.getDatumAfgehandeld()) &&
                Objects.equals(getEigenRisico(), schade.getEigenRisico()) &&
                Objects.equals(getOmschrijving(), schade.getOmschrijving());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPolis(), getSchadeNummerMaatschappij(), getSchadeNummerTussenPersoon(), getSoortSchade(), getSoortSchadeOngedefinieerd(), getLocatie(), getStatusSchade(), getDatumTijdSchade(), getDatumTijdMelding(), getDatumAfgehandeld(), getEigenRisico(), getOmschrijving());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("polis", polis).append("schadeNummerMaatschappij", schadeNummerMaatschappij).append("schadeNummerTussenPersoon", schadeNummerTussenPersoon).append("soortSchade", soortSchade).append("soortSchadeOngedefinieerd", soortSchadeOngedefinieerd).append("locatie", locatie).append("statusSchade", statusSchade).append("datumTijdSchade", datumTijdSchade).append("datumTijdMelding", datumTijdMelding).append("datumAfgehandeld", datumAfgehandeld).append("eigenRisico", eigenRisico).append("omschrijving", omschrijving).toString();
    }
}
