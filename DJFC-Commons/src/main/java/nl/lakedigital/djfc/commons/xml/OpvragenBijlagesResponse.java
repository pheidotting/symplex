package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonBijlage;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenBijlagesResponse {
    private List<JsonBijlage> bijlages;

    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = newArrayList();
        }
        return bijlages;
    }

    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }
}
