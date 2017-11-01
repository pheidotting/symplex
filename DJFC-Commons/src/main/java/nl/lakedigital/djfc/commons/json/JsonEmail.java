package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class JsonEmail extends JsonCommunicatieProduct {
    private String onderwerp;
    private String emailadres;

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonEmail)) {
            return false;
        }

        JsonEmail email = (JsonEmail) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getOnderwerp(), email.getOnderwerp()).append(getEmailadres(), email.getEmailadres()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getOnderwerp()).append(getEmailadres()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("onderwerp", onderwerp).append("emailadres", emailadres).toString();
    }
}
