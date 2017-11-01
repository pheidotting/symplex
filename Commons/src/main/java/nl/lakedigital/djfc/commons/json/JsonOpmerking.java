package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;

public class JsonOpmerking extends AbstracteJsonEntiteitMetSoortEnId implements Serializable, Comparable<JsonOpmerking> {
    private static final long serialVersionUID = -2035670222129537280L;

    private Long id;
    private String tijd;
    private String opmerking;
    private String medewerker;
    private Long medewerkerId;
    private String tekstBackup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public String getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(String medewerker) {
        this.medewerker = medewerker;
    }

    public Long getMedewerkerId() {
        return medewerkerId;
    }

    public void setMedewerkerId(Long medewerkerId) {
        this.medewerkerId = medewerkerId;
    }

    public String getTekstBackup() {
        return tekstBackup;
    }

    public void setTekstBackup(String tekstBackup) {
        this.tekstBackup = tekstBackup;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((opmerking == null) ? 0 : opmerking.hashCode());
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
        JsonOpmerking other = (JsonOpmerking) obj;
        return new EqualsBuilder().append(id, other.id).append(opmerking, other.opmerking).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Opmerking [id=");
        builder.append(id);
        builder.append(", tijd=");
        builder.append(tijd);
        builder.append(", opmerking=");
        builder.append(opmerking);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(JsonOpmerking o) {
        return tijd.compareTo(o.tijd) * -1;
    }
}
