package nl.dias.domein;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BIJLAGE")
@NamedQueries({//
        @NamedQuery(name = "Bijlage.zoekBijlagesBijEntiteit", query = "select b from Bijlage b where b.soortBijlage = :soortBijlage and b.entiteitId = :entiteitId")//
})
public class Bijlage implements Serializable {
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

    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTBIJLAGE")
    private SoortEntiteit soortBijlage;

    @Column(name = "ENTITEITID")
    private Long entiteitId;

    @Column(name = "S3")
    private String s3Identificatie;

    @Column(name = "OMSCHRIJVING")
    private String omschrijving;

    public Long getId() {
        return id;
    }

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

    public SoortEntiteit getSoortBijlage() {
        return soortBijlage;
    }

    public void setSoortBijlage(SoortEntiteit soortBijlage) {
        this.soortBijlage = soortBijlage;
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

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bijlage{");
        sb.append("id=").append(id);
        sb.append(", soortBijlage=").append(soortBijlage);
        sb.append(", s3Identificatie='").append(s3Identificatie).append('\'');
        sb.append(", omschrijving='").append(omschrijving).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Bijlage)) {
            return false;
        }
        Bijlage rhs = (Bijlage) object;

        return new EqualsBuilder().append(this.id, rhs.id).append(this.soortBijlage, rhs.soortBijlage).append(this.s3Identificatie, rhs.s3Identificatie).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.soortBijlage).append(this.s3Identificatie).toHashCode();
    }
}
