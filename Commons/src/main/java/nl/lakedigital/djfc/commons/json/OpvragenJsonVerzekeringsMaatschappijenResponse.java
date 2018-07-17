package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class OpvragenJsonVerzekeringsMaatschappijenResponse {
    List<JsonVerzekeringsMaatschappij> jsonVerzekeringsMaatschappijs;

    public List<JsonVerzekeringsMaatschappij> getJsonVerzekeringsMaatschappijs() {
        if (jsonVerzekeringsMaatschappijs == null) {
            jsonVerzekeringsMaatschappijs = new ArrayList<>();
        }
        return jsonVerzekeringsMaatschappijs;
    }

    public void setJsonVerzekeringsMaatschappijs(List<JsonVerzekeringsMaatschappij> jsonVerzekeringsMaatschappijs) {
        this.jsonVerzekeringsMaatschappijs = jsonVerzekeringsMaatschappijs;
    }
}
