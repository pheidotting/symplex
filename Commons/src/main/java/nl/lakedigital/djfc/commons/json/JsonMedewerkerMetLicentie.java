package nl.lakedigital.djfc.commons.json;

public class JsonMedewerkerMetLicentie extends JsonMedewerker {
    private String licentieType;

    public String getLicentieType() {
        return licentieType;
    }

    public void setLicentieType(String licentieType) {
        this.licentieType = licentieType;
    }
}