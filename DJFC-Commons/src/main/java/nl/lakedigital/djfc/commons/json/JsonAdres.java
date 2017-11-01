package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class JsonAdres extends AbstracteJsonEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = 2361944992062349932L;

    private Long id;
    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private String soortAdres;

    public JsonAdres() {
    }

    public Long getId() {
        return id;
    }

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
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getSoortAdres() {
        return soortAdres;
    }

    public void setSoortAdres(String soortAdres) {
        this.soortAdres = soortAdres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonAdres)) {
            return false;
        }

        JsonAdres jsonAdres = (JsonAdres) o;

        return new EqualsBuilder().append(getId(), jsonAdres.getId()).append(getStraat(), jsonAdres.getStraat()).append(getHuisnummer(), jsonAdres.getHuisnummer()).append(getToevoeging(), jsonAdres.getToevoeging()).append(getPostcode(), jsonAdres.getPostcode()).append(getPlaats(), jsonAdres.getPlaats()).append(getSoortAdres(), jsonAdres.getSoortAdres()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getStraat()).append(getHuisnummer()).append(getToevoeging()).append(getPostcode()).append(getPlaats()).append(getSoortAdres()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("straat", straat).append("huisnummer", huisnummer).append("toevoeging", toevoeging).append("postcode", postcode).append("plaats", plaats).append("soortAdres", soortAdres).append("soortEntiteit", soortEntiteit).append("entiteitId", entiteitId).append("parentIdentificatie", parentIdentificatie).append("identificatie", identificatie).toString();
    }
}
