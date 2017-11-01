package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class JsonRekeningNummer extends AbstracteJsonEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = 7872029330278528593L;

    private Long id;
    private String bic;
    private String rekeningnummer;
    private List<String> errors;

    public JsonRekeningNummer() {
    }

    public JsonRekeningNummer(Long id, String bic, String rekeningnummer) {
        super();
        this.id = id;
        this.bic = bic;
        this.rekeningnummer = rekeningnummer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bic).append(id).append(rekeningnummer).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonRekeningNummer other = (JsonRekeningNummer) obj;

        return new EqualsBuilder().append(bic, other.bic).append(id, other.id).append(rekeningnummer, other.rekeningnummer).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RekeningNummer [id=");
        builder.append(id);
        builder.append(", bic=");
        builder.append(bic);
        builder.append(", rekeningnummer=");
        builder.append(rekeningnummer);
        builder.append("]");
        return builder.toString();
    }
}
