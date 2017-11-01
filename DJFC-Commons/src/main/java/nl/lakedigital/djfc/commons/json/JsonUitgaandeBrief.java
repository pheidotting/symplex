package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class JsonUitgaandeBrief extends JsonBrief {
    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonBrief)) {
            return false;
        }

        JsonUitgaandeBrief brief = (JsonUitgaandeBrief) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getStraat(), brief.getStraat()).append(getHuisnummer(), brief.getHuisnummer()).append(getToevoeging(), brief.getToevoeging()).append(getPostcode(), brief.getPostcode()).append(getPlaats(), brief.getPlaats()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getStraat()).append(getHuisnummer()).append(getToevoeging()).append(getPostcode()).append(getPlaats()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("super", super.toString()).append("straat", straat).append("huisnummer", huisnummer).append("toevoeging", toevoeging).append("postcode", postcode).append("plaats", plaats).toString();
    }
}
