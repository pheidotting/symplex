package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class JsonLijstRelaties {
    private List<JsonRelatie> jsonRelaties;

    public List<JsonRelatie> getJsonRelaties() {
        if (jsonRelaties == null) {
            jsonRelaties = new ArrayList<>();
        }
        return jsonRelaties;
    }

    public void setJsonRelaties(List<JsonRelatie> jsonRelaties) {
        this.jsonRelaties = jsonRelaties;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(jsonRelaties).toHashCode();
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
        JsonLijstRelaties other = (JsonLijstRelaties) obj;

        return new EqualsBuilder().append(jsonRelaties, other.jsonRelaties).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JsonLijstRelaties [jsonRelaties=");
        builder.append(jsonRelaties);
        builder.append("]");
        return builder.toString();
    }
}
