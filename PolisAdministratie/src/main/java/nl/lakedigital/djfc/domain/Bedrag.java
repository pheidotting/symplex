package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Bedrag implements Serializable {
    private static final long serialVersionUID = -5780505649958523939L;

    @Column(name = "BEDRAG")
    private Double bedrag;

    protected Bedrag() {
    }

    public Bedrag(String bedrag) {
        this(Double.parseDouble(bedrag));
    }

    public Bedrag(Long bedrag) {
        this(bedrag.doubleValue());
    }

    public Bedrag(Double bedrag) {
        this.bedrag = bedrag;
    }

    public Double getBedrag() {
        return bedrag;
    }

    public void setBedrag(Double bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bedrag == null) ? 0 : bedrag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Bedrag other = (Bedrag) obj;
        return new EqualsBuilder().append(bedrag, other.bedrag).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bedrag [bedrag=");
        builder.append(bedrag);
        builder.append("]");
        return builder.toString();
    }
}
