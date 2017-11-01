package nl.lakedigital.djfc.commons.json;

public class JsonSoortHypotheek implements Comparable<JsonSoortHypotheek> {
    private Long id;
    private String omschrijving;
    private boolean ingebruik;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public boolean isIngebruik() {
        return ingebruik;
    }

    public void setIngebruik(boolean ingebruik) {
        this.ingebruik = ingebruik;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (ingebruik ? 1231 : 1237);
        result = prime * result + ((omschrijving == null) ? 0 : omschrijving.hashCode());
        return result;
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
        JsonSoortHypotheek other = (JsonSoortHypotheek) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (ingebruik != other.ingebruik) {
            return false;
        }
        if (omschrijving == null) {
            if (other.omschrijving != null) {
                return false;
            }
        } else if (!omschrijving.equals(other.omschrijving)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(JsonSoortHypotheek o) {
        return omschrijving.compareTo(o.getOmschrijving());
    }
}
