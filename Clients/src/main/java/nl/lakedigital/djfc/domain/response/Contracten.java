package nl.lakedigital.djfc.domain.response;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Contracten {
    private List<Bijlage> bijlages;
    private List<GroepBijlages> groepBijlages = newArrayList();
    private String identificatie;

    public List<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new ArrayList<>();
        }
        return bijlages;
    }

    public void setBijlages(List<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public List<GroepBijlages> getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(List<GroepBijlages> groepBijlages) {
        this.groepBijlages = groepBijlages;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }
}
