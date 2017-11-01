package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class JsonHypotheekPakket {
    private Long id;
    private List<JsonHypotheek> hypotheken;
    private String totaalBedrag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JsonHypotheek> getHypotheken() {
        if (hypotheken == null) {
            hypotheken = new ArrayList<JsonHypotheek>();
        }
        return hypotheken;
    }

    public void setHypotheken(List<JsonHypotheek> hypotheken) {
        this.hypotheken = hypotheken;
    }

    public String getTotaalBedrag() {
        return totaalBedrag;
    }

    public void setTotaalBedrag(String totaalBedrag) {
        this.totaalBedrag = totaalBedrag;
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonHypotheekPakket)) {
            return false;
        }
        JsonHypotheekPakket rhs = (JsonHypotheekPakket) object;
        return new EqualsBuilder().append(this.hypotheken, rhs.hypotheken).isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.hypotheken).toHashCode();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("hypotheken", this.hypotheken).toString();
    }
}
