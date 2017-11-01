package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class JsonRisicoAnalyse implements ObjectMetJsonOpmerkingen, ObjectMetJsonBijlages {
    private Long id;
    private Long bedrijf;
    private List<JsonBijlage> bijlages;
    private List<JsonOpmerking> opmerkingen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Long bedrijf) {
        this.bedrijf = bedrijf;
    }

    @Override
    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new ArrayList<>();
        }
        return bijlages;
    }

    @Override
    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }

    @Override
    public List<JsonOpmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    @Override
    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
