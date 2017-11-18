package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Inloggen {
    private String identificatie;
    private String wachtwoord;
    private List<String> errors;

    public Inloggen() {
        //moet wat in staan van sonar
    }

    public Inloggen(String identificatie, String wachtwoord) {
        this.identificatie = identificatie;
        this.wachtwoord = wachtwoord;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public List<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Inloggen)) {
            return false;
        }
        Inloggen rhs = (Inloggen) object;
        return new EqualsBuilder().append(this.wachtwoord, rhs.wachtwoord).append(this.identificatie, rhs.identificatie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.wachtwoord).append(this.identificatie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("wachtwoord", this.wachtwoord).append("identificatie", this.identificatie).toString();
    }
}
