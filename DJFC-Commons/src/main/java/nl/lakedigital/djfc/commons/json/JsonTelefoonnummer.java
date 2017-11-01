package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class JsonTelefoonnummer extends AbstracteJsonEntiteitMetSoortEnId implements Serializable {
    private static final long serialVersionUID = 3624291960507458499L;

    private Long id;
    private String telefoonnummer;
    private String soort;
    private String omschrijving;
    private List<String> errors;

    public JsonTelefoonnummer() {
    }

    public JsonTelefoonnummer(Long id, String telefoonnummer, String soort, String omschrijving) {
        super();
        this.id = id;
        this.telefoonnummer = telefoonnummer;
        this.soort = soort;
        this.omschrijving = omschrijving;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(telefoonnummer).append(omschrijving).toHashCode();
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
        JsonTelefoonnummer other = (JsonTelefoonnummer) obj;

        return new EqualsBuilder().append(id, other.id).append(soort, other.soort).append(telefoonnummer, other.telefoonnummer).append(omschrijving, other.omschrijving).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Telefoonnummer [id=");
        builder.append(id);
        builder.append(", telefoonnummer=");
        builder.append(telefoonnummer);
        builder.append(", soort=");
        builder.append(soort);
        builder.append(", omschrijving=");
        builder.append(omschrijving);
        builder.append("]");
        return builder.toString();
    }
}