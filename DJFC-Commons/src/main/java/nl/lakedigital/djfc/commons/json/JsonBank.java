package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JsonBank {
    private Long id;
    private String naam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonBank)) {
            return false;
        }
        JsonBank rhs = (JsonBank) object;
        return new EqualsBuilder().append(this.id, rhs.id).append(this.naam, rhs.naam).isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.naam).toHashCode();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("naam", this.naam).toString();
    }

}
