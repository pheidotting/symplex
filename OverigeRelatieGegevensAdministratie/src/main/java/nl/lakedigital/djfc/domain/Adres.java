package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Audited
@Entity
@Table(name = "ADRES")
@NamedQueries({//
        @NamedQuery(name = "Adres.zoekBijEntiteit", query = "select a from Adres a where a.soortEntiteit = :soortEntiteit and a.entiteitId = :entiteitId"),//
        @NamedQuery(name = "Adres.zoeken", query = "select a from Adres a where a.straat like :zoekTerm or a.plaats like :zoekTerm"), //
        @NamedQuery(name = "Adres.zoekenOpAdres", query = "select a from Adres a where a.straat like :zoekTerm"), //
        @NamedQuery(name = "Adres.zoekenOpPostcode", query = "select a from Adres a where a.postcode like :zoekTerm"), //
        @NamedQuery(name = "Adres.zoekenOpPlaats", query = "select a from Adres a where a.plaats like :zoekTerm") //
})
public class Adres extends AbstracteEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = 2361944992062349932L;

    public enum SoortAdres {
        WOONADRES, POSTADRES, RISICOADRES, FACTUURADRES;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "STRAAT")
    private String straat;
    @Column(name = "HUISNUMMER")
    private Long huisnummer;
    @Column(name = "TOEVOEGING")
    private String toevoeging;
    @Column(length = 6, name = "POSTCODE")
    private String postcode;
    @Column(name = "PLAATS")
    private String plaats;
    @Column(name = "SOORT")
    @Enumerated(EnumType.STRING)
    private SoortAdres soortAdres;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Long getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Long huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        if (postcode != null) {
            this.postcode = postcode.trim().replace(" ", "");
        }
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public SoortAdres getSoortAdres() {
        return soortAdres;
    }

    public void setSoortAdres(SoortAdres soortAdres) {
        this.soortAdres = soortAdres;
    }

    public boolean isCompleet() {
        return isNotBlank(straat) && huisnummer != null && isNotBlank(postcode) && isNotBlank(plaats);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Adres)) {
            return false;
        }

        Adres adres = (Adres) o;

        return new EqualsBuilder().append(getStraat(), adres.getStraat()).append(getHuisnummer(), adres.getHuisnummer()).append(getToevoeging(), adres.getToevoeging()).append(getPostcode(), adres.getPostcode()).append(getPlaats(), adres.getPlaats()).append(getSoortAdres(), adres.getSoortAdres()).append(getEntiteitId(), adres.getEntiteitId()).append(getSoortEntiteit(), adres.getSoortEntiteit()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getStraat()).append(getHuisnummer()).append(getToevoeging()).append(getPostcode()).append(getPlaats()).append(getSoortAdres()).append(getEntiteitId()).append(getSoortEntiteit()).toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("straat", straat).append("huisnummer", huisnummer).append("toevoeging", toevoeging).append("postcode", postcode).append("plaats", plaats).append("soortAdres", soortAdres).append("compleet", isCompleet()).append("entiteitId", getEntiteitId()).append("soortEntiteit", getSoortEntiteit()).toString();
    }
}
