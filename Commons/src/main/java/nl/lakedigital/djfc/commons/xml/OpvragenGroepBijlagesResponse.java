package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenGroepBijlagesResponse {
    private List<JsonGroepBijlages> jsonGroepBijlages;

    public List<JsonGroepBijlages> getBijlages() {
        if (jsonGroepBijlages == null) {
            jsonGroepBijlages = newArrayList();
        }
        return jsonGroepBijlages;
    }

    public void setBijlages(List<JsonGroepBijlages> jsonGroepBijlages) {
        this.jsonGroepBijlages = jsonGroepBijlages;
    }
}
