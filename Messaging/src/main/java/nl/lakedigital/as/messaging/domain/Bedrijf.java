package nl.lakedigital.as.messaging.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Bedrijf implements BedrijfOfPersoon {
    private String naam;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("naam", naam).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Bedrijf)) {
            return false;
        }

        Bedrijf bedrijf = (Bedrijf) o;

        return new EqualsBuilder().append(getNaam(), bedrijf.getNaam()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getNaam()).toHashCode();
    }
}
