package nl.lakedigital.djfc.commons.json;

import com.google.common.collect.Lists;

import java.util.List;

public class JsonJaarCijfers implements ObjectMetJsonBijlages, ObjectMetJsonOpmerkingen {
    private Long id;
    private Long jaar;
    private Long bedrijf;
    private List<JsonBijlage> bijlages;
    private List<JsonOpmerking> opmerkingen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJaar() {
        return jaar;
    }

    public void setJaar(Long jaar) {
        this.jaar = jaar;
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
            bijlages = Lists.newArrayList();
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
            opmerkingen = Lists.newArrayList();
        }
        return opmerkingen;
    }

    @Override
    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
