package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Audited
@Entity
@Table(name = "BIJLAGE")
@NamedQueries({//
        @NamedQuery(name = "Bijlage.zoekBijEntiteit", query = "select b from Bijlage b where b.soortEntiteit = :soortEntiteit and b.entiteitId = :entiteitId and groepBijlages is null"),//
        @NamedQuery(name = "Bijlage.zoekBijEntiteitMetGroep", query = "select b from Bijlage b where b.soortEntiteit = :soortEntiteit and b.entiteitId = :entiteitId and groepBijlages is not null"),//
        @NamedQuery(name = "Bijlage.zoekBijEntiteitMetGroepAlles", query = "select b from Bijlage b where groepBijlages is not null"),//
        @NamedQuery(name = "Bijlage.zoeken", query = "select b from Bijlage b where b.omschrijving like :zoekTerm")//
})
public class Bijlage extends AbstracteEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = 5743959281799187372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BESTANDSNAAM", length = 500)
    private String bestandsNaam;
    @Column(name = "UPLOADMOMENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadMoment;
    @Column(name = "S3")
    private String s3Identificatie;
    @Column(name = "OMSCHRIJVING")
    private String omschrijving;
    @JoinColumn(name = "GROEPBIJLAGES")
    @ManyToOne
    private GroepBijlages groepBijlages;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsNaam() {
        return bestandsNaam;
    }

    public void setBestandsNaam(String bestandsNaam) {
        this.bestandsNaam = bestandsNaam;
    }

    public LocalDateTime getUploadMoment() {
        return new LocalDateTime(uploadMoment);
    }

    public void setUploadMoment(LocalDateTime uploadMoment) {
        this.uploadMoment = uploadMoment.toDate();
    }

    public String getS3Identificatie() {
        return s3Identificatie;
    }

    public void setS3Identificatie(String s3Identificatie) {
        this.s3Identificatie = s3Identificatie;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public GroepBijlages getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(GroepBijlages groepBijlages) {
        this.groepBijlages = groepBijlages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Bijlage)) {
            return false;
        }

        Bijlage bijlage = (Bijlage) o;

        return new EqualsBuilder().append(getId(), bijlage.getId()).append(getBestandsNaam(), bijlage.getBestandsNaam()).append(getOmschrijving(), bijlage.getOmschrijving()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getBestandsNaam()).append(getOmschrijving()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("bestandsNaam", bestandsNaam).append("uploadMoment", uploadMoment).append("s3Identificatie", s3Identificatie).append("omschrijving", omschrijving).toString();
    }
}
